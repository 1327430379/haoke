package cn.haoke.mgmt.util;

import cn.haoke.center.user.dto.FuncDto;
import lombok.Data;

import java.util.List;

@Data
public class SystemUserLoginInfoVo {

    /**
     * 用户id
     */
    private Long userId;

    /***
     * 用户名
     */
    private String username;

    /***
     * 手机号码
     */
    private String mobile;

    /***
     * 姓名
     */
    private String name;

    /***
     * 角色
     */
    private String role;

    /***
     * 功能集合
     */
    private List<FuncDto> funcList;
}
