package cn.haoke.center.user.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserSearchDto implements Serializable {

    /***
     * 姓名
     */
    private String name;

    /***
     * 手机号码
     */
    private String mobile;

    /***
     * 角色
     */
    private String role;

    private Integer pageNum;

    private Integer pageSize;

}
