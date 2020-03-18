package cn.haoke.mgmt.controller;

import cn.haoke.center.user.dto.UserSearchDto;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.controller.base.AbstractBaseController;
import cn.haoke.mgmt.dto.LoginReqDto;
import cn.haoke.mgmt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public RestResponse loginUser(@RequestBody @Valid LoginReqDto dto){
        return userService.loginUser(dto);
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

}
