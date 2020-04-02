package cn.haoke.center.user.api;

import cn.haoke.center.user.dto.LoginUserDto;
import cn.haoke.center.user.dto.TokenDto;
import cn.haoke.center.user.dto.UserSearchDto;
import cn.haoke.center.user.pojo.UserEo;
import cn.haoke.common.exception.BusinessException;
import cn.haoke.common.vo.RestResponse;
import com.github.pagehelper.PageInfo;

public interface ApiUserService {

    /***
     * 用户登录管理系统
     * @param loginCode
     * @param password
     * @return
     */
    RestResponse<TokenDto> loginManageSystem(String loginCode, String password) throws Exception;


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

    /***
     * 登录app
     * @param username 用户名
     * @param password 密码
     * @return  RestResponse<TokenDto>
     */
    RestResponse<LoginUserDto> loginApp(String username, String password) throws Exception;

    /***
     * 通过用户名查询用户
     * @param username
     * @return
     */
    RestResponse<UserEo> queryByUsername(String username);

    /***
     * 保存用户
     * @param userEo
     * @return
     */
    RestResponse<Void> saveUser(UserEo userEo);

    /***
     * 通过id查询
     * @param userId 用户Id
     * @return
     */
    RestResponse<UserEo> queryById(Long userId);
}
