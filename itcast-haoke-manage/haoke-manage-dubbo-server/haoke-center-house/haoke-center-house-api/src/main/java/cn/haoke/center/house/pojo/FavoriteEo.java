package cn.haoke.center.house.pojo;

import cn.haoke.common.pojo.BasePojo;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/***
 * 收藏实体类
 */
@Data
@TableName("hk_favorite")
public class FavoriteEo extends BasePojo {


    private String houseId;

    private Long userId;

    private String houseTitle;
}
