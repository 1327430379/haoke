package cn.haoke.center.user.mapper;

import cn.haoke.center.user.pojo.UserEo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<UserEo> {


    @Select({"select u.* from hk_user u where " +
            "password = #{password} and (username=#{loginCode} or mobile = #{loginCode})"})
    UserEo loginManageSystem(@Param("loginCode")String loginCode, @Param("password")String password);



}
