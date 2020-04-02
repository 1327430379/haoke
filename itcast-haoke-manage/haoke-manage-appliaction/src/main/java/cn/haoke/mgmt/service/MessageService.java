package cn.haoke.mgmt.service;

import cn.haoke.center.user.api.ApiMessageService;
import cn.haoke.center.user.pojo.MessageEo;
import cn.haoke.common.vo.PageInfo;
import cn.haoke.common.vo.RestResponse;
import org.apache.dubbo.config.annotation.Reference;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Reference(version = "1.0.0")
    private ApiMessageService apiMessageService;

    public PageInfo<MessageEo> listMessage(Long userId, Integer readStatus, Integer pageNum, Integer pageSize){
        MessageEo messageEo = new MessageEo();
        messageEo.setUserId(userId);
        messageEo.setReadStatus(readStatus);
       return apiMessageService.listMessageByPage(messageEo,pageNum,pageSize).getData();
    }

    public RestResponse changeReadStatusByMessageId(Long messageId, Integer readStatus){
        apiMessageService.updateReadStatusById(messageId,readStatus);
        return new RestResponse();
    }
}
