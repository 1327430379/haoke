package cn.haoke.center.house.pojo;

import cn.haoke.common.pojo.BasePojo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 回答
 */
@Data
@TableName(value = "hk_answer")
@AllArgsConstructor
@NoArgsConstructor
public class AnswerEo extends BasePojo {

    /**
     * 回答内容
     */
    @TableField(value = "answer_content")
    private String answerContent;

    /**
     * 问题id
     */
    @TableField(value = "question_id")
    private Long questionId;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 回答状态（0：未审核，1：已审核，2：驳回
     */
    @TableField(value = "answer_status")
    private Integer answerStatus;

}
