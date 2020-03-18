package cn.haoke.center.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisService  {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /***
     * 向redis中设值
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean set(String key,String value){
        boolean result = false;
        try{
            redisTemplate.opsForValue().set(key,value);
            result =true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
