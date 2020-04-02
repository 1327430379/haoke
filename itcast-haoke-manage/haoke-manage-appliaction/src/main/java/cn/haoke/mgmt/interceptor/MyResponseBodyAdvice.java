//package cn.haoke.mgmt.interceptor;
//
//import cn.haoke.mgmt.controller.GraphQLController;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.MethodParameter;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.http.server.ServletServerHttpRequest;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
//
//import java.time.Duration;
//
//@ControllerAdvice
//public class MyResponseBodyAdvice implements ResponseBodyAdvice {
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
//
//    private ObjectMapper mapper = new ObjectMapper();
//
//    @Override
//    public boolean supports(MethodParameter returnType, Class convertType) {
//        if (returnType.hasMethodAnnotation(GetMapping.class)) {
//            return true;
//        }
//        if (returnType.hasMethodAnnotation(PostMapping.class) &&
//                StringUtils.equals(GraphQLController.class.getName(),
//                        returnType.getExecutable().getDeclaringClass().getName())) {
//            return true;
//        }
//
//        return false;
//    }
//
//    @Override
//    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType mediaType, Class aClass, ServerHttpRequest request, ServerHttpResponse serverHttpResponse) {
//        try {
//
//            String redisKey =
//                    RedisCacheInterceptor.cerateRedisKey(((ServletServerHttpRequest)
//                            request).getServletRequest());
//            String redisValue;
//            if(body instanceof String){
//                redisValue = (String)body;
//            }else{
//                redisValue = mapper.writeValueAsString(body);
//            }
//            this.redisTemplate.opsForValue().set(redisKey,redisValue, Duration.ofHours(1));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//        return body;
//    }
//}
