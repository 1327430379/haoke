package cn.haoke.center.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements Serializable {


    private Long id;

    private Date createTime;

    private Date updateTime;

    /***
     * 用户名
     */
    private String username;

    /***
     * 密码
     */
    private String password;

    /***
     * 手机号码
     */
    private String mobile;

    /***
     * 角色
     */
    private String role;

    /***
     * 启用status(1:启用，0:禁用)
     */
    private Integer status;

    /***
     * 性别
     */
    private String gender;

    /***
     * 姓名
     */
    private String name;

    /***
     * 认证状态（1：认证，0：未认证)
     */
    private Integer identityStatus;

    /***
     * 认证信息
     */
    private String identityMsg;

    /***
     * 地址
     */
    private String address;

    /***
     * 头像
     */
    private String avatar;

    /***
     * 职业
     */
    private String job;

    /***
     * 籍贯
     */
    private String nativePlace;

    /***
     * 昵称
     */
    private String nickname;

    /***
     * 身份证号码
     */
    private String idcardNumber;
}
