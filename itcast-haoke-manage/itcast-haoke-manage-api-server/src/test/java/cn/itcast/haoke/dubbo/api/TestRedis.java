package cn.itcast.haoke.dubbo.api;


import net.bytebuddy.asm.Advice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    
    
    @Test
    public void testSave(){
        for (int i = 0; i < 10; i++) {
            redisTemplate.opsForValue().set("key_i"+i,"value_"+i);
        }
        Set<String> keys = redisTemplate.keys("key_*");

        keys.forEach(key->{
            System.out.println(key);
            redisTemplate.delete(key);
        });
    }
}
