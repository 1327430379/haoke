package cn.haoke.mgmt.controller;

import cn.haoke.center.user.dto.UserSearchDto;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.controller.base.AbstractBaseController;
import cn.haoke.mgmt.dto.AppLoginDto;
import cn.haoke.mgmt.dto.LoginReqDto;
import cn.haoke.mgmt.service.UserService;
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

        if(StringUtils.equals(dto.getLoginCode(),"user")&&StringUtils.equals(dto.getPassword(),"123456")){
            data.put("status","ok");
            data.put("currentAuthority","user");
            return data;
        }
        data.put("status","error");
        data.put("errorMsg","用户不存在！");
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
    public RestResponse getUserList(@RequestBody UserSearchDto dto){
        return userService.getUserList(dto);
    }

//
//    /**
//     * 租客详情
//     * @param id 用户id
//     * @return RestResponse
//     */
//    @GetMapping("/tenant/detail/{id}")
//    public RestResponse getTenantDetail(@PathVariable Long id){
//        return userService.getTenantDetail(id);
//    }

    @GetMapping("/currentUser")
    public Map<String,Object> currentUser(){
        Map<String,Object> data = new HashMap<>();
        data.put("name","lingzan");
        data.put("avatar","https://i0.hdslb.com/bfs/face/950e091db191c2415c05cde9c10a3c02f58ed25c.jpg@150w_150h.jpg");
        return data;
    }


    @PostMapping("/register")
    public RestResponse registerUser(@RequestBody AppLoginDto dto){
        return userService.registerUser(dto);
    }





}
