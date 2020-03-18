package cn.haoke.center.house.pojo;


import cn.haoke.common.pojo.BasePojo;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("hk_house_onwer")
public class FangDong extends BasePojo {

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

}
