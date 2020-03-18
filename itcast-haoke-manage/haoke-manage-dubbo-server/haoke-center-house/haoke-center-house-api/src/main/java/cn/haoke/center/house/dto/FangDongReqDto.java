package cn.haoke.center.house.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FangDongReqDto implements Serializable {

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
     * 页码
     */
    private Integer pageNum;

    /***
     * 页大小
     */
    private Integer pageSize;
}
