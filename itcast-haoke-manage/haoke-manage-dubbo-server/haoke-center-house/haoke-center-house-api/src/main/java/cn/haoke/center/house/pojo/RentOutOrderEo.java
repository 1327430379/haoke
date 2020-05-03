package cn.haoke.center.house.pojo;

import cn.haoke.common.pojo.BasePojo;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName(value = "hk_rent_out_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentOutOrderEo extends BasePojo {
    /**
     * 房源id
     */
    private String houseId;

    /**
     * 房东id
     */
    private Long houseOwnerId;

    /***
     * 租客id
     */
    private Long tenantId;

    /***
     * 状态（0:创建待房东同意，1：待付款，2：已付款）
     */
    private Integer status;

    /***
     * 交易金额
     */
    private Integer money;

    /**
     * 备注
     */
    private String remark;

    /***
     * 租赁开始时间
     */
    private Date rentStartTime;

    /**
     * 租赁结束时间
     */
    private Date rentEndTime;

    /***
     * 交易时间
     */
    private Date tradingTime;

}

