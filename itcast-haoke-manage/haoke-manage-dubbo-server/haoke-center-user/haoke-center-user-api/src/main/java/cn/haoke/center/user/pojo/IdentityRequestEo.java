package cn.haoke.center.user.pojo;


import cn.haoke.common.pojo.BasePojo;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@TableName(value = "hk_identity_request")
public class IdentityRequestEo extends BasePojo {
    
    private Long userId;

    @NotNull(message = "姓名不能为空！")
    private String name;
    @NotNull(message = "身份证号码不能为空！")
    private String idcardNumber;
    @NotNull(message = "认证图片不能为空！")
    private String identityImage;

    private String identityMsg;

    private Integer identityStatus;

}
