package cn.haoke.mgmt.service;

import cn.haoke.center.user.api.ApiUserService;
import cn.haoke.center.user.dto.TokenDto;
import cn.haoke.center.user.dto.UserSearchDto;
import cn.haoke.center.user.pojo.UserEo;
import cn.haoke.center.user.vo.UserInfoCacheVo;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.dto.AppLoginDto;
import cn.haoke.mgmt.dto.LoginReqDto;
import cn.haoke.mgmt.util.TableResultUtils;
import cn.haoke.mgmt.vo.TableResult;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Reference(version = "1.0.0")
    private ApiUserService apiUserService;


    public RestResponse<TokenDto> loginUser(LoginReqDto dto) {

        //登录后台
        return apiUserService.loginManageSystem(dto.getLoginCode(), dto.getPassword());

    }

    public RestResponse<TableResult> getUserList(UserSearchDto dto) {
        PageInfo pageInfo = apiUserService.queryUserList(dto).getData();
        TableResult tableResult = TableResultUtils.pageInfo2TableResult(pageInfo);
        return new RestResponse<>(tableResult);
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
        return apiUserService.loginApp(username, password);
    }

    public RestResponse registerUser(AppLoginDto dto) {
        UserEo data = apiUserService.queryByUsername(dto.getUsername()).getData();
        if (data != null) {
            return new RestResponse(RestResponse.failCode, "此用户名已存在！");
        }
        UserEo userEo = new UserEo();
        userEo.setUsername(dto.getUsername());
        userEo.setPassword(dto.getPassword());
        return apiUserService.saveUser(userEo);
    }

    public RestResponse<Void> updateUser(UserEo userEo) {
        return apiUserService.saveUser(userEo);
    }

    public RestResponse<UserEo> queryById(Long id) {
        return apiUserService.queryById(id);
    }


}
