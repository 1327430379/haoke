package cn.haoke.center.house.pojo;


import cn.haoke.common.pojo.BasePojo;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/***
 * 租房请求eo
 */
@Data
@TableName(value = "hk_rent_house_request")
public class RentHouseRequestEo extends BasePojo {

    /***
     * 房源id
     */
    private Long houseId;

    /***
     * 房源名称
     */
    private String houseName;

    /***
     * 租客名称
     */
    private String tenantName;

    /***
     * 租客id
     */
    private Long tenantId;

    /***
     * 租客手机号码
     */
    private String tenantMobile;

    /***
     * 合同赶止时间
     */
    private Date contractStartTime;

    /***
     * 租金
     */
    private Double rentMoney;

    /***
     * 请求状态（0：待确认，1：已确认，2：待付款，3：已逾期）
     */
    private Integer requestStatus;

    /***
     * 确认时间
     */
    private Date confirmTime;

    /***
     * 签约时间
     */
    private Date signedTime;

    /***
     * 完成时间
     */
    private Date finishedTime;
}

