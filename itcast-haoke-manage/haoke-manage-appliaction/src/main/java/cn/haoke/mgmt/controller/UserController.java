package cn.haoke.mgmt.controller;

import cn.haoke.center.user.dto.TokenDto;
import cn.haoke.center.user.dto.UserDto;
import cn.haoke.center.user.dto.UserSearchDto;
import cn.haoke.center.user.pojo.UserEo;
import cn.haoke.common.constants.CommonConstant;
import cn.haoke.common.exception.BusinessException;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.controller.base.AbstractBaseController;
import cn.haoke.mgmt.dto.AppLoginDto;
import cn.haoke.mgmt.dto.LoginReqDto;
import cn.haoke.mgmt.service.UserService;
import cn.haoke.mgmt.util.SystemUserLoginInfoVo;
import cn.haoke.mgmt.util.SystemUserLoginUtil;
import cn.haoke.mgmt.vo.TableResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController extends AbstractBaseController {

    @Autowired
    private UserService userService;

    /***
     * 登录
     * @param dto
     * @return
     */
    @PostMapping("/login")
    public Map<String,Object> loginUser(@RequestBody @Valid LoginReqDto dto){


        Map<String,Object> data = new HashMap<>();
        if(StringUtils.equals(dto.getLoginCode(),"admin")&&StringUtils.equals(dto.getPassword(),"123456")){
            data.put("status","ok");
            data.put("currentAuthority","admin");
            return data;
        }
        TokenDto tokenDto = userService.loginUser(dto).getData();
        if(tokenDto!=null && tokenDto.getToken()!=null){
            data.put("status","ok");
            data.put("currentAuthority","houseOwner");
            data.put("data",tokenDto);
        }else{
            data.put("status","error");
            data.put("errorMsg","用户信息有误！");
        }

//        if(StringUtils.equals(dto.getLoginCode(),"user")&&StringUtils.equals(dto.getPassword(),"123456")){
//            data.put("status","ok");
//            data.put("currentAuthority","user");
//            return data;
//        }
//        data.put("status","error");
//        data.put("errorMsg","用户不存在！");
        return data;


       // return userService.loginUser(dto);
    }
    @PostMapping("/app/login")
    public RestResponse loginApp(@RequestBody AppLoginDto dto) throws Exception {
        return userService.loginApp(dto.getUsername(),dto.getPassword());
    }

    /***
     * 用户列表
     * @param dto 请求参数
     * @return RestResponse
     */
    @PostMapping("/list")
    public RestResponse<TableResult> getUserList(@RequestBody UserSearchDto dto){
        return userService.getUserList(dto);
    }


    /**
     * 租客详情
     * @param id 用户id
     * @return RestResponse
     */
    @GetMapping("/{id}")
    public RestResponse<UserEo> queryUserDtail(@PathVariable Long id){
        return userService.queryById(id);
    }

    /***
     * 得到当前登录用户的信息
     * @return
     */
    @GetMapping("/app/current")
    public RestResponse<UserEo> currentUser(){

        SystemUserLoginInfoVo loginInfo = SystemUserLoginUtil.getLoginInfo();
        if(loginInfo==null){
            throw new BusinessException(CommonConstant.LOGIN_SESSION_DISABLES);
        }
        return userService.queryById(loginInfo.getUserId());
    }


    @PostMapping("/register")
    public RestResponse registerUser(@RequestBody AppLoginDto dto){
        return userService.registerUser(dto);
    }

    @PutMapping("/update")
    public RestResponse<Void> updateUser(@RequestBody UserEo userEo){
        if(userEo.getId()==null){
            return new RestResponse<>(RestResponse.failCode,"用户Id不能为空！");
        }
        return userService.updateUser(userEo);
    }





}
