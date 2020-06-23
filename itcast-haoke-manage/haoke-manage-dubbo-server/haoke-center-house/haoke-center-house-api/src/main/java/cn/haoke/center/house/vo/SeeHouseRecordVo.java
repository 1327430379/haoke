package cn.haoke.center.house.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeeHouseRecordVo implements Serializable {

    /**
     * id
     */
    private Long id;
    /***
     * 租客姓名
     */
    private String tenantName;

    /***
     * 租客Id
     */
    private Long tenantId;

    /***
     * 租客手机号码
     */
    private String tenantMobile;

    /***
     * 看房时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date seeHouseTime;

    /***
     * 请求状态（ 0：待确认，1：待看房，2：已取消，3：已完成）
     */
    private Integer seeHouseStatus;

    /***
     * 房源Id
     */
    private String houseId;

    /***
     * 房源名称
     */
    private String houseName;

    /***
     * 房源图片
     */
    private String houseImage;

    /***
     * 房源租金
     */
    private Integer houseRent;

    /***
     * 确认时间
     */
    private Date confirmTime;

    /***
     * 完成时间
     */
    private Date finishedTime;

    /***
     * 房东id
     */
    private Long houseOwnerId;

    /***
     * 房东姓名
     */
    private String houseOwnerName;

    /***
     * 房屋类型
     */
    private String houseType;


    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /***
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
