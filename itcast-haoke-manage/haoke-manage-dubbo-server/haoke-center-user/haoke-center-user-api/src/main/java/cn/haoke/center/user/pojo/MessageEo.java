package cn.haoke.center.user.pojo;

import cn.haoke.common.pojo.BasePojo;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("hk_message")
public class MessageEo extends BasePojo {

    /***
     * 标题
     */
    private String title;

    /***
     * 内容
     */
    private String content;

    /***
     * 类型（1：看房请求消息)
     */
    private Integer type;

    /***
     * 关联的数据id
     */
    private Long dataId;

    /***
     * 接收的用户id
     */
    private Long userId;

    /***
     * 发送者id
     */
    private Long senderId;

    /***
     * 已读状态（0：未读，1：已读）
     */
    private Integer readStatus;


}
