package cn.haoke.mgmt.util;

import lombok.Data;

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




}
