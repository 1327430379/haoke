package cn.haoke.mgmt.util;

import cn.haoke.center.user.constants.UserConstant;
import cn.haoke.center.user.pojo.UserEo;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class SystemUserLoginUtil {
    private static Logger logger = LoggerFactory.getLogger(SystemUserLoginUtil.class);

    private static RedisTemplate<String, String> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        SystemUserLoginUtil.redisTemplate = redisTemplate;
    }

    public static SystemUserLoginInfoVo getLoginInfo() {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = req.getHeader("authorization");
        if (StringUtils.isEmpty(token)) {
            token = req.getParameter("authorization");
        }
        //用户信息处理
        SystemUserLoginInfoVo infoVo = null;
        String cacheInfo = redisTemplate.opsForValue().get(UserConstant.LOGIN_USER_CACHE_PRE + token);
        if (StringUtils.isEmpty(cacheInfo)) {
            return infoVo;
        }
        infoVo = JSON.parseObject(cacheInfo, SystemUserLoginInfoVo.class);
        return infoVo;
    }
    public static UserEo getCurrentLoginInfo(){
        String cacheInfo = redisTemplate.opsForValue().get(UserConstant.LOGIN_USER_MGMT_CACHE_PRE);
        UserEo userEo = null;
        if(StringUtils.isNotEmpty(cacheInfo)){
            userEo = JSON.parseObject(cacheInfo, UserEo.class);
        }
        return userEo;
    }


}
