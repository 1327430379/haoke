package cn.haoke.center.user.apiimpl;

import cn.haoke.center.user.api.ApiMessageService;
import cn.haoke.center.user.mapper.MessageMapper;
import cn.haoke.center.user.pojo.MessageEo;
import cn.haoke.common.vo.PageInfo;
import cn.haoke.common.vo.RestResponse;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(version = "1.0.0")
public class ApiMessageServiceImpl implements ApiMessageService {

    @Autowired
    private MessageMapper messageMapper;
    @Override
    public RestResponse<PageInfo<MessageEo>> listMessageByPage(MessageEo eo, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum,pageSize);
        Wrapper<MessageEo> wrapper = new QueryWrapper<>(eo);
        List<MessageEo> messageEoList = messageMapper.selectList(wrapper);
        return new RestResponse<>(new PageInfo<>(messageEoList));
    }

    @Override
    public RestResponse<Void> updateReadStatusById(Long id, Integer readStatus) {

        MessageEo messageEo = new MessageEo();
        messageEo.setId(id);
        messageEo.setReadStatus(readStatus);
         messageMapper.updateById(messageEo);
         return new RestResponse<>();
    }

    @Override
    public RestResponse<Void> updateReadStatusByUserId(Long userId, Integer readStatus) {
        MessageEo conditionEo = new MessageEo();
        conditionEo.setUserId(userId);

        Wrapper<MessageEo> wrapper = new UpdateWrapper<>(conditionEo);
        MessageEo updateEo = new MessageEo();
        updateEo.setReadStatus(readStatus);
        messageMapper.update(updateEo,wrapper);
        return new RestResponse<>();
    }

    @Override
    public RestResponse<Void> deleteMessageByUserId(Long userId) {
        if(null == userId){

        }
        MessageEo messageEo = new MessageEo();
        messageEo.setUserId(userId);
        Wrapper<MessageEo> wrapper = new QueryWrapper<>(messageEo);
        messageMapper.delete(wrapper);
        return  new RestResponse<Void>();
    }

    @Override
    public RestResponse<Void> asyncInsertMessage(MessageEo messageEo) {
        messageMapper.insert(messageEo);
        return  new RestResponse<Void>();
    }

    @Override
    public RestResponse<Void> asyncInsertBatchMessageList(List<MessageEo> messageEoList) {

        messageEoList.forEach(messageEo -> {
            messageMapper.insert(messageEo);
        });
        return  new RestResponse<Void>();
    }
}
