package cn.haoke.center.user.pojo;


import cn.haoke.common.pojo.BasePojo;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "hk_house_onwer")
public class HouseOwnerEo extends BasePojo {

    /**
     * 用户id
     */
    private Long userId;

    /***
     * 房源数
     */
    private Integer houseCount;


}
