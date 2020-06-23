package cn.haoke.center.house.api;

import cn.haoke.center.house.pojo.QuestionEo;
import cn.haoke.common.vo.RestResponse;

public interface ApiQuestionService {

    /**
     * 添加问答
     * @param questionEo
     * @return
     */
     RestResponse<Long> addQuestion(QuestionEo questionEo);
}
