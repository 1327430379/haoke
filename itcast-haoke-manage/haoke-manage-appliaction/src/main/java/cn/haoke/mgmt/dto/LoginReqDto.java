package cn.haoke.mgmt.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 登录dto
 */
@Data
public class LoginReqDto implements Serializable {

    /***
     * 登录的手机号或用户名
     */
    @NotNull
    private String loginCode;

    /***
     * 密码
     */
    @NotNull
    private String password;

    /**
     * 登录标识 （1：管理后台，2：app）
     */
    @NotNull
    private String loginFlag;
}
