package cn.haoke.im.service;

import cn.itcast.haoke.im.dao.MessageDAO;
import cn.itcast.haoke.im.pojo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageDAO messageDAO;

    public List<Message> queryMessageList(Long fromId, Long toId, Integer page, Integer rows) {
        List<Message> list = this.messageDAO.findListByFromAndTo(fromId, toId, page, rows);
        for (Message message : list) {
            if (message.getStatus().intValue() == 1) {
                // 修改消息状态为已读
                this.messageDAO.updateMessageState(message.getId(), 2);
            }
        }
        return list;
    }
}
