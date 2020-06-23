package cn.haoke.center.house.pojo;

import cn.haoke.common.pojo.BasePojo;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/***
 * 收藏实体类
 */
@TableName(value = "hk_favorite")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteEo extends BasePojo {


    private String houseId;

    private Long userId;

    private String houseTitle;


}
