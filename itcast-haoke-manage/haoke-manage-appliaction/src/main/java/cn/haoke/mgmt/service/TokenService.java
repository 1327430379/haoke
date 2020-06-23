package cn.haoke.mgmt.service;

import cn.haoke.center.user.api.ApiTokenService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service
public class TokenService {


    @Reference(version = "1.0.0")
    private ApiTokenService apiTokenService;
    public boolean checkUserToken(String token) {
        if(StringUtils.isBlank(token)){
            return false;
        }
        String[] split = token.split("_");
        if(split.length<2){
            return false;
        }
        if(!StringUtils.isNumeric(split[0])){
            return false;
        }
        return apiTokenService.checkToken(token);
    }
}
