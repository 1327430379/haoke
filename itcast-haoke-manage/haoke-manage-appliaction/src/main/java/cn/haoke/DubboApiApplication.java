package cn.haoke;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DubboApiApplication {

    public static void main(String[] args) {
        //整合了Es和Redis后，引发了netty的冲突，需要在启动类中加入
        System.setProperty("es.set.netty.runtime.available.processors", "false");

        SpringApplication.run(DubboApiApplication.class, args);
    }


//    @Bean
//    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        //1.创建redisTemplate模板
//        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
//        //2.关联redisConnectionFactory
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        //3.创建序列化类
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(String.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        //4.设置可见度
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        //5.启动默认的类型
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        //6.序列化类，对象映射设置
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//        //7.设置value的转化格式和key的转化格式
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//
//    }
}