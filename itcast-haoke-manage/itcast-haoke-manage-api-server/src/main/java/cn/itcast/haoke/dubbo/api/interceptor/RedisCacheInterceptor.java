package cn.itcast.haoke.dubbo.api.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class RedisCacheInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!StringUtils.equalsIgnoreCase(request.getMethod(),"get")){
            //非get请求，如果不是graphql请求，放行
            if(!StringUtils.equalsIgnoreCase(request.getRequestURI(),"/graphql")){
                return true;
            }
        }

        String data = redisTemplate.opsForValue().get(cerateRedisKey(request));
        if(StringUtils.isEmpty(data)){
            //缓存未命中
            return true;
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(data);
        return false;
    }

    public static String cerateRedisKey(HttpServletRequest request) throws IOException {

        String paramStr = request.getRequestURI();

        Map<String, String[]> parameterMap = request.getParameterMap();
        if(parameterMap.isEmpty()){
            paramStr += IOUtils.toString(request.getInputStream());
        }else{
            paramStr += mapper.writeValueAsString(request.getParameterMap());
        }
        String redisKey = "WEB_DATA" + DigestUtils.md5Hex(paramStr);
        return redisKey;
    }
}
