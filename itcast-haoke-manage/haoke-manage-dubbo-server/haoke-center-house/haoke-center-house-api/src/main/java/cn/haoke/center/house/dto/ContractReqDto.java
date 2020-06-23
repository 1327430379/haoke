package cn.haoke.center.house.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ContractReqDto implements Serializable {


    /***
     * 合同状态(0：待签约，1：履约中，2：已结束，3：已逾期)
     */
    private Integer contractStatus;


    /***
     * 房源名称
     */
    private String houseName;

    /***
     * 房东名称
     */
    private String ownerName;

    /***
     * 租客姓名
     */
    private String customerName;

    /***
     * 开始时间
     */
    private Date startTime;

    /***
     * 结束时间
     */
    private Date endTime;

    private Integer pageNum;

    private Integer pageSize;
}
