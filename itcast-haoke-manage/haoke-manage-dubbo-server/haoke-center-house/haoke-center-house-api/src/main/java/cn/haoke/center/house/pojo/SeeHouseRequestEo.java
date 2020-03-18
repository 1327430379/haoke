package cn.haoke.center.house.pojo;

import cn.haoke.common.pojo.BasePojo;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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
    private String tenantId;

    /***
     * 看房时间
     */
    private Date seeHouseTime;

    /***
     * 请求状态（ 0：待确认，1：待看房，2：已取消，3：已完成）
     */
    private Integer requestStatus;

    /***
     * 房源Id
     */
    private Long houseId;

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


}
