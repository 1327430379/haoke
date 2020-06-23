package cn.haoke.center.house.mapper;

import cn.haoke.center.house.pojo.SeeHouseRequestEo;
import cn.haoke.center.house.vo.SeeHouseRecordVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SeeHouseRequestMapper extends BaseMapper<SeeHouseRequestEo> {


    @Results(id = "seeHouseMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "tenantId", column = "tenant_id"),
            @Result(property = "tenantName", column = "tenant_name"),
            @Result(property = "tenantMobile", column = "tenant_mobile"),
            @Result(property = "seeHouseTime", column = "see_house_time"),
            @Result(property = "seeHouseStatus", column = "see_house_status"),
            @Result(property = "houseId", column = "house_id"),
            @Result(property = "houseName", column = "title"),
            @Result(property = "houseImage", column = "image"),
            @Result(property = "houseRent", column = "rent"),
            @Result(property = "houseType", column = "house_type"),
            @Result(property = "confirmTime", column = "confirm_time"),
            @Result(property = "finishedTime", column = "finished_time"),
            @Result(property = "houseOwnerId", column = "house_owner_id"),
            @Result(property = "houseOwnerName", column = "house_owner_name"),
            @Result(property = "updateTime", column = "update_time"),
    })
    @Select({"select  s.*,h.title,h.image,h.rent,h.house_type " +
            "from hk_see_house_request s left join hk_house_resources h on s.house_id = h.id " +
            "where s.id = #{id}"})
    SeeHouseRecordVo queryById(@Param("id") Long id);


    @Select({"<script>select  s.*,h.title,h.image,h.rent,h.house_type " +
            "from hk_see_house_request s left join hk_house_resources h on s.house_id = h.id " +
            "where 1=1 " +
            "<if test='eo.tenantId!=null'>AND s.tenant_id=#{eo.tenantId} </if>" +
            "<if test='eo.houseOwnerId!=null'>AND s.house_owner_id = #{eo.houseOwnerId} </if>" +
            "<if test='eo.seeHouseStatus!=null'>AND s.see_house_status=#{eo.seeHouseStatus} </if>" +
            "<if test='eo.tenantId!=null'>AND s.tenant_id=#{eo.tenantId} </if>" +
//            "<if test='eo.tenantName!=null and eo.tenantName!=\"\"'>AND s.tenant_name like CONCAT('%',#{eo.tenantName} ,'%')</if>" +
            "</script>"})
    @ResultMap("seeHouseMap")
    List<SeeHouseRecordVo> queryList(@Param("eo") SeeHouseRequestEo eo);


}
