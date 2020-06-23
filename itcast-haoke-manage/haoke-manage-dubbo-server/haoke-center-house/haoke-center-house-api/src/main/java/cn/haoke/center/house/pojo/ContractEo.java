package cn.haoke.center.house.pojo;

import cn.haoke.common.pojo.BasePojo;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "hk_contract")
public class ContractEo extends BasePojo {

    /***
     * 合同编号
     */
    private String contractCode;

    /***
     * 合同状态(0：待签约，1：履约中，2：已结束，3：已逾期)
     */
    private Integer contractStatus;

    /**
     * 合同详细信息
     */
    private String contractContent;

    /***
     * 房源名称
     */
    private String houseName;

    /***
     * 房源id
     */
    private String houseId;

    /***
     * 房东Id
     */
    private Long ownerId;

    /***
     * 房东名称
     */
    private String ownerName;

    /***
     * 租客id
     */
    private Long customerId;

    /***
     * 租客姓名
     */
    private String customerName;

    /**
     * 租客手机号
     */
    private String customerMobile;

    /***
     * 结束时间
     */
    private Date endTime;


}
