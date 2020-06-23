package cn.haoke.center.house.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class RentOutOrderVo implements Serializable {

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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date rentStartTime;

    /**
     * 租赁结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date rentEndTime;

    /***
     * 交易时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date tradingTime;

    /***
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /***
     * 房东id
     */
    private Long houseOwnerId;

    /**
     * 房源id
     */
    private String houseId;

    /***
     * 房源标题
     */
    private String houseTitle;

    /***
     * 房源图片
     */
    private String houseImage;




}
