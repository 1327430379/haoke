package cn.haoke.center.house.pojo;

import cn.haoke.common.pojo.BasePojo;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/***
 * 看房请求eo
 */
@Data
@TableName(value = "hk_see_house_request")
public class SeeHouseRequestEo extends BasePojo {

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
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


}
