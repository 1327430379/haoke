package cn.haoke.center.user.apiimpl;

import cn.haoke.center.user.api.ApiTokenService;
import cn.haoke.center.user.constants.UserConstant;
import cn.haoke.center.user.dto.TokenDto;
import cn.haoke.center.user.utils.TokenHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

@Service(version = "1.0.0")
public class ApiTokenServiceImpl implements ApiTokenService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private TokenHelper tokenHelper;

    @Override
    public boolean checkToken(String token) {
        TokenDto dto = tokenHelper.get(token);
        if (dto == null) {
            return false;
        }
        //判断缓存中token是否过期
        String cacheToken = redisTemplate.opsForValue().get(UserConstant.USER_TOKEN_PRE + String.valueOf(dto.getUserId()));
        if (StringUtils.isBlank(cacheToken)) {
            return false;
        }
        return true;
    }
}
