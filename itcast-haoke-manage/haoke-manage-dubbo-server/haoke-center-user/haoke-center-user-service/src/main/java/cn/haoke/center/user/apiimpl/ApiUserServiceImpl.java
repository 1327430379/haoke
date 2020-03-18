package cn.haoke.center.user.apiimpl;

import cn.haoke.center.user.api.ApiUserService;
import cn.haoke.center.user.constants.CustomConstant;
import cn.haoke.center.user.constants.UserConstant;
import cn.haoke.center.user.constants.enums.RoleEnum;
import cn.haoke.center.user.dto.TokenDto;
import cn.haoke.center.user.dto.UserSearchDto;
import cn.haoke.center.user.mapper.UserMapper;
import cn.haoke.center.user.pojo.UserEo;
import cn.haoke.center.user.utils.TokenHelper;
import cn.haoke.center.user.vo.UserVo;
import cn.haoke.common.vo.RestResponse;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(version = "1.0.0")
public class ApiUserServiceImpl implements ApiUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TokenHelper tokenHelper;

    @Override
    public RestResponse<TokenDto> loginManageSystem(String loginCode, String password) {
        UserEo user = userMapper.loginManageSystem(loginCode, password);
        if (user == null) {
            return new RestResponse<>(CustomConstant.ERROR_CODE, "账号密码有误！");
        }
        if (UserConstant.DISABLED.equals(user.getStatus())) {
            return new RestResponse<>(CustomConstant.ERROR_CODE, "账号被禁用！");
        }
        if (RoleEnum.USER.getRole().equals(user.getRole())) {
            return new RestResponse<>(CustomConstant.ERROR_CODE, "您没有权限登录此系统！");
        }
        TokenDto token = tokenHelper.createToken(user.getId());
        return new RestResponse<>(token);
    }

    @Override
    public RestResponse<PageInfo> queryUserList(UserSearchDto dto) {

        if (dto.getPageNum() != null && dto.getPageSize() != null) {
            PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        }
        //先查出所有用户
        UserEo userEo = new UserEo();
        userEo.setStatus(UserConstant.ENABLED);
        QueryWrapper<UserEo> wrapper = new QueryWrapper<>(userEo);
        if (StringUtils.isNotEmpty(dto.getName())) {
            wrapper.like("name", "%" + dto.getName() + "%");
        }
        if (StringUtils.isNotEmpty(dto.getMobile())) {
            wrapper.like("mobile", "%" + dto.getMobile() + "%");
        }
        List<UserEo> userEos = userMapper.selectList(wrapper);
        PageInfo<UserEo> pageInfo = new PageInfo<>(userEos);
        return new RestResponse<>(pageInfo);
    }

    @Override
    public RestResponse<UserEo> getUserById(Long id) {
        return new RestResponse<>(userMapper.selectById(id));
    }
}
