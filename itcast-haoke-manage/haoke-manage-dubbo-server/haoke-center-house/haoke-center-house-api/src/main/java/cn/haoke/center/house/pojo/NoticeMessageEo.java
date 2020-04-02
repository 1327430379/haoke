package cn.haoke.center.house.pojo;

import cn.haoke.common.pojo.BasePojo;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName(value = "hk_notice_message")
public class NoticeMessageEo extends BasePojo {


    /***
     * 目标用户类型（1：租客用户，2：房东用户）
     */
    private Integer targetType;

    /***
     * 标题
     */
    private String title;

    /***
     * 通知内容
     */
    private String content;

    /***
     * 关联的数据id
     */
    private Long dataId;

    /**
     * 目标用户Id
     */
    private Long userId;

    /***
     * 发送者id
     */
    private Long senderId;

    /***
     * 已读状态
     */
    private Integer readStatus;

}
