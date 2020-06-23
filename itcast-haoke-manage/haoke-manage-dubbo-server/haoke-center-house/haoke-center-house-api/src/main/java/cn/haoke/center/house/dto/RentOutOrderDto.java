package cn.haoke.center.house.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentOutOrderDto implements Serializable {

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

    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 页大小
     */
    private Integer pageSize;
}
