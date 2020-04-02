package cn.haoke.mgmt.service;

import cn.haoke.center.user.api.ApiUserService;
import cn.haoke.center.user.dto.UserSearchDto;
import cn.haoke.center.user.pojo.UserEo;
import cn.haoke.center.user.vo.UserInfoCacheVo;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.dto.AppLoginDto;
import cn.haoke.mgmt.dto.LoginReqDto;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Reference(version = "1.0.0")
    private ApiUserService apiUserService;


    public RestResponse loginUser(LoginReqDto dto) throws Exception {
        if (dto.getLoginFlag().equals("1")) {
            //登录后台
            return apiUserService.loginManageSystem(dto.getLoginCode(), dto.getPassword());
        } else {
            //登录app
            return null;
        }
    }

    public RestResponse getUserList(UserSearchDto dto) {
        return apiUserService.queryUserList(dto);
    }


    public UserInfoCacheVo getCurrentUserInfo(Long userId, String token) {
        UserEo userEo = apiUserService.getUserById(userId).getData();
        if (null == userEo) {
            return null;
        }
        //用户信息组装
        UserInfoCacheVo cacheVo = new UserInfoCacheVo();
        BeanUtils.copyProperties(userEo, cacheVo);
        //用户菜单 todo

        return cacheVo;
    }


    public RestResponse loginApp(String username, String password) throws Exception {
        return apiUserService.loginApp(username,password);
    }

    public RestResponse registerUser(AppLoginDto dto) {
        UserEo data = apiUserService.queryByUsername(dto.getUsername()).getData();
        if(data!=null){
            return new RestResponse(RestResponse.failCode,"此用户名已存在！");
        }
        UserEo userEo = new UserEo();
        userEo.setUsername(dto.getUsername());
        userEo.setPassword(dto.getPassword());
        return apiUserService.saveUser(userEo);
    }
}
