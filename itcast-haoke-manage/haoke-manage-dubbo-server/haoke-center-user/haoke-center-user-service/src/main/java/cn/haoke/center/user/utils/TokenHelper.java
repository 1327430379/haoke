package cn.haoke.center.user.utils;

import cn.haoke.center.user.constants.UserConstant;
import cn.haoke.center.user.dto.TokenDto;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import static cn.haoke.center.user.constants.UserConstant.*;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class TokenHelper {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public TokenDto createToken(Long id) {
        String token = UUID.randomUUID().toString().replace("-", "");
        token = String.valueOf(id) + "_" + token;
        TokenDto tokenDto = new TokenDto(id, token);
        redisTemplate.opsForValue().set(USER_TOKEN_PRE + String.valueOf(id), token, Duration.ofHours(1));
        return tokenDto;
    }

    public boolean checkToken(TokenDto dto) {
        boolean result = false;
        Long userId = dto.getUserId();
        String token = dto.getToken();
        String authToken = redisTemplate.opsForValue().get(userId);
        if (authToken != null && authToken.equals(token)) {
            redisTemplate.expire(USER_TOKEN_PRE + String.valueOf(userId), USER_TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
            result = true;
        }
        return result;
    }

    public boolean delete(Integer userId) {
        return redisTemplate.delete(USER_TOKEN_PRE + String.valueOf(userId));
    }

    public TokenDto get(String authStr) {
        TokenDto dto = null;
        if (StringUtils.isNotEmpty(authStr)) {
            String[] strArr = authStr.split("_");
            if (strArr.length == 2) {
                long userId = Long.parseLong(strArr[0]);
                String token = strArr[1];
                dto = new TokenDto(userId, token);
            }
        }
        return dto;
    }
}
