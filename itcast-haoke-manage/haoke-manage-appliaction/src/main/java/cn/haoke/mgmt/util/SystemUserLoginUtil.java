package cn.haoke.mgmt.util;

import cn.haoke.center.user.constants.UserConstant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class SystemUserLoginUtil {
    private static Logger logger = LoggerFactory.getLogger(SystemUserLoginUtil.class);

    private static StringRedisTemplate redisTemplate;

    @Autowired
    public static StringRedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public static void setRedisTemplate(StringRedisTemplate redisTemplate) {
        SystemUserLoginUtil.redisTemplate = redisTemplate;
    }

    public static SystemUserLoginInfoVo getLoginInfo(){
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = req.getHeader("token");
        if(StringUtils.isEmpty(token)){
            token = req.getParameter("token");
        }
        //用户信息处理
        SystemUserLoginInfoVo infoVo = new SystemUserLoginInfoVo();
        String cacheInfo = redisTemplate.opsForValue().get(UserConstant.LOGIN_USER_CACHE_PRE + token);
        if(StringUtils.isEmpty(cacheInfo)){
            return infoVo;
        }
        infoVo = JSON.parseObject(cacheInfo,SystemUserLoginInfoVo.class);
        return infoVo;
    }


}
