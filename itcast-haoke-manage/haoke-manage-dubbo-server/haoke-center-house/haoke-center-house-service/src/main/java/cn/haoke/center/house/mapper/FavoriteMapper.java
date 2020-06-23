package cn.haoke.center.house.mapper;

import cn.haoke.center.house.pojo.FavoriteEo;
import cn.haoke.center.house.vo.FavoriteVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FavoriteMapper extends BaseMapper<FavoriteEo> {


    @Select({"<script>" +
            "select f.*,h.image,h.rent,h.title " +
            "from hk_favorite f left join hk_house_resources h on f.house_id = h.id where 1=1 " +
            "<if test='eo.houseTitle!=null and eo.houseTitle!=\"\"'>and f.house_title like CONCAT('%','#{eo.houseTitle}','%')</if>"+
            "<if test='eo.userId!=null'>and f.user_id = #{eo.userId}</if>"+
            "<if test='eo.houseId!=null' >and f.house_id = #{eo.houseId}</if>"+
            "</script>"})
    @Results(id = "favoriteMap",value = {
            @Result(property = "id",column = "id"),
            @Result(property = "houseId",column = "house_id"),
            @Result(property = "userId",column = "user_id"),
            @Result(property = "houseTitle",column = "title"),
            @Result(property = "houseImage",column = "image"),
            @Result(property = "houseRent",column = "rent")
    })
    List<FavoriteVo> queryList(@Param("eo") FavoriteEo eo);
}
