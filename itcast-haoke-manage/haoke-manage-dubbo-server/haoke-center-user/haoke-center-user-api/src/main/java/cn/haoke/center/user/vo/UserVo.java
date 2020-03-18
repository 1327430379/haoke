package cn.haoke.center.user.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserVo implements Serializable {

    /***
     * 用户id
     */
    private Long userId;

    /***
     * 手机号码
     */
    private String mobile;

    /***
     * 角色
     */
    private String role;


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
     * 身份证号码
     */
    private String idcardNumber;

    /***
     * 职业
     */
    private String job;
}
