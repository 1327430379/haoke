package cn.haoke.center.house.mapper;

import cn.haoke.center.house.pojo.RentOutOrderEo;
import cn.haoke.center.house.vo.RentOutOrderVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RentOutOrderMapper extends BaseMapper<RentOutOrderEo> {


    @Select({"<script>" +
            "select o.*,r.house_owner_id as houseOwnerId,r.image as houseImage,r.title as houseTitle" +
            " from hk_rent_out_order o left join hk_house_resources r on o.house_id = r.id where 1=1 " +
            "<if test='eo.tenantId!=null'>and o.tenant_id = #{eo.tenantId} </if>"+
            "<if test='eo.houseOwnerId!=null'>and r.house_owner_id = #{eo.houseOwnerId} </if>"+
            "<if test='eo.status!=null'>and o.status = #{eo.status} </if>"+
            "</script>"})
    List<RentOutOrderVo> queryList(@Param("eo") RentOutOrderEo eo);

}
