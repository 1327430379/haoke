package cn.haoke.center.house.pojo;

import cn.haoke.common.pojo.BasePojo;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@TableName("tb_ad")
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Ad extends BasePojo {

    private static final long serialVersionUID = -493439243433085768L;

    /***
     * 广告类型
     */
    private Integer type;
    /***
     * 描述
     */
    private String title;
    /***
     * 图片URL地址
     */
    private String url;
}