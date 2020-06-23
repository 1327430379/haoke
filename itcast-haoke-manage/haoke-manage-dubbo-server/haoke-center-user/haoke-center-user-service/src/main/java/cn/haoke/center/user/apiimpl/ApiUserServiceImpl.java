package cn.haoke.center.user.apiimpl;

import cn.haoke.center.user.api.ApiUserService;
import cn.haoke.center.user.constants.CustomConstant;
import cn.haoke.center.user.constants.UserConstant;
import cn.haoke.center.user.constants.enums.RoleEnum;
import cn.haoke.center.user.dto.LoginUserDto;
import cn.haoke.center.user.dto.TokenDto;
import cn.haoke.center.user.dto.UserSearchDto;
import cn.haoke.center.user.mapper.UserMapper;
import cn.haoke.center.user.pojo.UserEo;
import cn.haoke.center.user.utils.TokenHelper;
import cn.haoke.center.user.vo.UserVo;
import cn.haoke.common.exception.BusinessException;
import cn.haoke.common.utils.IdUtils;
import cn.haoke.common.vo.RestResponse;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(version = "1.0.0")
public class ApiUserServiceImpl implements ApiUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TokenHelper tokenHelper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public RestResponse<TokenDto> loginManageSystem(String loginCode, String password) {
        UserEo user = userMapper.loginManageSystem(loginCode, password);
        validateUser(user);
        TokenDto token = tokenHelper.createToken(user.getId());
        //保存用户会话到redis
        redisTemplate.opsForValue().set(UserConstant.LOGIN_USER_MGMT_CACHE_PRE, JSON.toJSONString(user), Duration.ofHours(1));
        return new RestResponse<>(token);
    }

    @Override
    public RestResponse<PageInfo> queryUserList(UserSearchDto dto) {

        if (dto.getPageNum() != null && dto.getPageSize() != null) {
            PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        }
        //先查出所有用户
        UserEo userEo = new UserEo();
        BeanUtils.copyProperties(dto,userEo);
        userEo.setStatus(UserConstant.ENABLED);
        QueryWrapper<UserEo> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(dto.getName())) {
            wrapper.like("name", "%" + dto.getName() + "%");
            userEo.setName(null);
        }
        if (StringUtils.isNotEmpty(dto.getMobile())) {
            wrapper.like("mobile", "%" + dto.getMobile() + "%");
            userEo.setMobile(null);
        }
        wrapper.setEntity(userEo);
        List<UserEo> userEos = userMapper.selectList(wrapper);
        PageInfo<UserEo> pageInfo = new PageInfo<>(userEos);
        return new RestResponse<>(pageInfo);
    }

    @Override
    public RestResponse<UserEo> getUserById(Long id) {
        return new RestResponse<>(userMapper.selectById(id));
    }

    @Override
    public RestResponse<LoginUserDto> loginApp(String username, String password) throws Exception {
        UserEo userEo = UserEo.builder().username(username).password(password).build();
        Wrapper<UserEo> wrapper = new QueryWrapper<>(userEo);
        UserEo user = userMapper.selectOne(wrapper);
        if (user == null) {
            throw new BusinessException("账号密码有误！");
        }
        if (UserConstant.DISABLED.equals(user.getStatus())) {
            throw new BusinessException("账号被禁用！");
        }
//        String userToken = redisTemplate.opsForValue().get(UserConstant.USER_TOKEN_PRE + user.getId());
//        boolean tokenExist = true;
//        if (StringUtils.isEmpty(userToken)) {
//            tokenExist = false;
//        }

        TokenDto tokenDto = tokenHelper.createToken(user.getId());
        LoginUserDto userDto = new LoginUserDto();
        BeanUtils.copyProperties(user, userDto);
        userDto.setUserId(user.getId());
        userDto.setToken(tokenDto.getToken());

        //先判断缓存中是否有缓存消息，没有则将用户信息保存到缓存中
        String userJson = objectMapper.writeValueAsString(userDto);
        redisTemplate.opsForValue().set(UserConstant.LOGIN_USER_CACHE_PRE + userDto.getToken(), userJson, Duration.ofHours(1));
        return new RestResponse<>(RestResponse.successCode, userDto);
//        if (!tokenExist) {
//
//        }


    }

    @Override
    public RestResponse<UserEo> queryByUsername(String username) {
        UserEo userEo = new UserEo();
        userEo.setUsername(username);
        Wrapper<UserEo> wrapper = new QueryWrapper<>(userEo);
        UserEo resultEo = userMapper.selectOne(wrapper);
        return new RestResponse<>(resultEo);
    }

    @Override
    public RestResponse<Void> saveUser(UserEo userEo) {
        Date date = new Date();
        userEo.setUpdateTime(date);
        if (userEo.getId() != null) {
            userMapper.updateById(userEo);
        }
        if (StringUtils.isEmpty(userEo.getRole())) {
            userEo.setRole(RoleEnum.USER.getRole());
            userEo.setId(IdUtils.getLongId());
            userEo.setCreateTime(date);
            userMapper.insert(userEo);
        }

        return new RestResponse<>();
    }

    @Override
    public RestResponse<UserEo> queryById(Long userId) {
        return new RestResponse<>(userMapper.selectById(userId));
    }

    @Override
    public RestResponse<PageInfo<UserEo>> queryList(UserSearchDto dto) {

        UserEo eo = new UserEo();
        BeanUtils.copyProperties(dto, eo);
        QueryWrapper<UserEo> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(dto.getName())) {
            wrapper.like("name", "%" + eo.getName() + "%");
            eo.setName(null);
        }
        if (StringUtils.isNotBlank(dto.getMobile())) {
            wrapper.like("mobile", "%" + eo.getMobile() + "%");
        }
        if (dto.getPageNum() != null && dto.getPageSize() != null) {
            PageHelper.startPage(dto.getPageNum(),dto.getPageSize());
        }
        wrapper.setEntity(eo);
        List<UserEo> userEos = userMapper.selectList(wrapper);
        return new RestResponse<>(new PageInfo<>(userEos));
    }

    /***
     * 生成token
     * @param user
     * @return
     */
    private void validateUser(UserEo user) {
        if (user == null) {
            throw new BusinessException("账号密码有误！");
        }
        if (UserConstant.DISABLED.equals(user.getStatus())) {
            throw new BusinessException("账号被禁用！");
        }
        if (RoleEnum.USER.getRole().equals(user.getRole())) {
            throw new BusinessException("您没有权限登录此系统！");
        }
    }
}
