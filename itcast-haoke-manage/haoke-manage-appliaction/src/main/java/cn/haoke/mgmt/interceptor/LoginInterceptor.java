package cn.haoke.mgmt.interceptor;

import cn.haoke.center.user.constants.UserConstant;
import cn.haoke.center.user.vo.UserInfoCacheVo;
import cn.haoke.mgmt.service.TokenService;
import cn.haoke.mgmt.service.UserService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Autowired
    private UrlWhiteSet urlWhiteSet;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //options请求直接通过


        String method = request.getMethod();
        if ("OPTIONS".equals(method)) {
            return true;
        }
        if(true){

            return true;
        }
        //获取当前请求路径uri,判断是否为白名单
        String uri = request.getRequestURI().replace(request.getContextPath(), "");
        if (urlWhiteSet.contains(uri)) {
            return true;
        }
        //获取token userId
        String token = request.getHeader("authorization");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("authorization");
        }

        if (StringUtils.isEmpty(token)||"null".equals(token)) {
            //未登录，返回401状态码
            response.setStatus(401);
            return false;
        }
        logger.info("uri={},token={}", uri, token);
        //检查token是否有效
        if (!tokenService.checkUserToken(token)) {
            //未登录，返回401状态码
            response.setStatus(401);
            return false;
        }

        Long userId = Long.parseLong(token.split("_")[0]);
        if (userId == null) {
            userId = Long.parseLong(request.getParameter("userId"));
        }



        //从缓存中获取用户信息
        String cacheInfo = redisTemplate.opsForValue().get(UserConstant.LOGIN_USER_CACHE_PRE + token);
        if (StringUtils.isEmpty(cacheInfo)) {
            UserInfoCacheVo userInfo = userService.getCurrentUserInfo(userId, token);
            //存入缓存
            redisTemplate.opsForValue().set(UserConstant.LOGIN_USER_CACHE_PRE+token, JSON.toJSONString(userInfo));
        }else{
            //刷新缓存失效时间
            redisTemplate.expire(UserConstant.LOGIN_USER_CACHE_PRE + token,UserConstant.USER_TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
        }
        return true;
    }
}
