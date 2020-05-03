package cn.haoke.center.house.vo;

import java.io.Serializable;
import java.util.Date;

public class RentOutOrderDetailVo implements Serializable {

    /**
     * 房源id
     */
    private String houseId;

    /**
     * 房源标题
     */
    private String houseTitle;

    /**
     * 房源图片
     */
    private String houseImage;

    /***
     * 租客id
     */
    private Long tenantId;

    /***
     * 租客姓名
     */
    private String tenantName;

    /**
     * 租客手机号
     */
    private String tenantMobile;

    /***
     * 租客身份证号码
     */
    private String tenantIdcardNumber;

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
