package cn.haoke.center.house.apiimpl;

import cn.haoke.center.house.api.ApiQuestionService;
import cn.haoke.center.house.mapper.QuestionMapper;
import cn.haoke.center.house.pojo.QuestionEo;
import cn.haoke.common.utils.IdUtils;
import cn.haoke.common.vo.RestResponse;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Service(version = "1.0.0")
public class ApiQuestionServiceImpl implements ApiQuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public RestResponse<Long> addQuestion(QuestionEo questionEo){
        Long id = IdUtils.getLongId();
        questionEo.setId(id);
        Date date = new Date();
        questionEo.setCreateTime(date);
        questionEo.setUpdateTime(date);
        questionMapper.insert(questionEo);
        return new RestResponse<>(questionEo.getId());
    }
}
