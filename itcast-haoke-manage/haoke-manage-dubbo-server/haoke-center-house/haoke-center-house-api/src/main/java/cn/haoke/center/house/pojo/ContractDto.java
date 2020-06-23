package cn.haoke.center.house.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class ContractDto implements Serializable {



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
    @NotNull(message = "房源名称不能为空！")
    private String houseName;

    /***
     * 房源id
     */
    @NotNull(message = "房源id不能为空！")
    private String houseId;

    /***
     * 房东Id
     */
    @NotNull(message = "房东id不能为空！")
    private Long ownerId;

    /***
     * 房东名称
     */
    @NotNull(message = "房东名称不能为空！")
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
    @NotNull(message = "结束日期不能为空！")
    private Date endTime;
}
