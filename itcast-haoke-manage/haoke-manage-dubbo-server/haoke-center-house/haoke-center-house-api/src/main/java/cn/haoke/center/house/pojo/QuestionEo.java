package cn.haoke.center.house.pojo;

import cn.haoke.common.pojo.BasePojo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "hk_questions")
public class QuestionEo extends BasePojo {
    /***
     * 问题名称
     */
    @TableField(value = "question_name")
    private String questionName;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 问题标签
     */
    @TableField(value = "question_tag")
    private String questionTag;

    /**
     * 问题描述
     */
    @TableField(value = "question_desc")
    private String questionDesc;

    /**
     * 回答人数
    */
    @TableField(value = "reply_count")
    private Integer replyCount;
}
