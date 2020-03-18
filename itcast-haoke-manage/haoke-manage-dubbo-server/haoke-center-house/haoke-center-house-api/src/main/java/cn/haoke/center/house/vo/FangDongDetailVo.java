package cn.haoke.center.house.vo;

import cn.haoke.center.house.pojo.HouseResources;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 房东详情vo
 */
@Data
public class FangDongDetailVo implements Serializable {
    /***
     * 姓名
     */
    private String name;

    /***
     *手机号码
     */
    private String mobile;

    /***
     * 性别
     */
    private Integer sex;

    /***
     * 身份证号码
     */
    private String idcardNumber;

    /***
     * 房源数量
     */
    private Integer houseCount;

    /***
     * 认证状态
     */
    private Integer identityStatus;

    /***
     * 认证信息（图片）
     */
    private String identityMsg;

    /***
     * 房源信息表
     */
    private List<HouseResources> houseResourcesList;


}
