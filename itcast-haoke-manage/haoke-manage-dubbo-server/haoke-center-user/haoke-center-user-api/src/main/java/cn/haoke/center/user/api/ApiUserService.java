package cn.haoke.center.user.api;

import cn.haoke.center.user.dto.TokenDto;
import cn.haoke.center.user.dto.UserSearchDto;
import cn.haoke.center.user.pojo.UserEo;
import cn.haoke.common.vo.RestResponse;
import com.github.pagehelper.PageInfo;

public interface ApiUserService {

    /***
     * 用户登录管理系统
     * @param loginCode
     * @param password
     * @return
     */
    RestResponse<TokenDto> loginManageSystem(String loginCode, String password);


    /***
     * 查询所有用户列表包括房东和租户
     */
    RestResponse<PageInfo> queryUserList(UserSearchDto dto);

    /***
     * 通过id查询
     * @param id
     * @return
     */
    RestResponse<UserEo> getUserById(Long id);
}
