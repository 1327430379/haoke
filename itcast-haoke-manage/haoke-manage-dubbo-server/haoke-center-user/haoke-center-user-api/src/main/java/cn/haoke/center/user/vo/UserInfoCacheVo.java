package cn.haoke.center.user.vo;

import cn.haoke.center.user.dto.FuncDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/***
 * 用户信息缓存vo
 */
@Data
public class UserInfoCacheVo implements Serializable {

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
