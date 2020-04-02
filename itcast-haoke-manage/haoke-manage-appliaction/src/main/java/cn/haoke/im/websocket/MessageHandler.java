package cn.haoke.im.websocket;

import cn.haoke.center.user.api.ApiUserService;
import cn.haoke.center.user.pojo.UserEo;
import cn.haoke.im.dao.MessageDAO;
import cn.haoke.im.pojo.Message;
import cn.haoke.im.pojo.User;
import cn.haoke.im.pojo.User.UserBuilder;
import cn.haoke.im.pojo.UserData;
import cn.haoke.im.service.SessionService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//import org.apache.rocketmq.spring.annotation.MessageModel;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.apache.rocketmq.spring.core.RocketMQTemplate;

@Component
//@RocketMQMessageListener(
//        topic = "haoke-im-send-message-topic",
//        selectorExpression = "SEND_MSG",
//        messageModel = MessageModel.BROADCASTING,
//        consumerGroup = "haoke-im-group"
//)
public class MessageHandler extends TextWebSocketHandler {

    @Autowired
    private MessageDAO messageDAO;

    @Autowired
    private SessionService sessionService;

    @Reference(version = "1.0.0")
    private ApiUserService apiUserService;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final Map<Long, WebSocketSession> SESSIONS = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long uid = (Long) session.getAttributes().get("uid");
        // 将当前用户的session放置到map中，后面会使用相应的session通信
        SESSIONS.put(uid, session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage
            textMessage) throws Exception {
        Long uid = (Long) session.getAttributes().get("uid");

        JsonNode jsonNode = MAPPER.readTree(textMessage.getPayload());
        Long toId = jsonNode.get("toId").asLong();
        String msg = jsonNode.get("msg").asText();
        Message message = new Message();
        UserEo targetEo = apiUserService.queryById(toId).getData();
        UserEo sendEo = apiUserService.queryById(uid).getData();
        if (sendEo != null) {
            User user = new User();
            user.setId(sendEo.getId());
            user.setUsername(StringUtils.isEmpty(sendEo.getName()) ? sendEo.getName() : sendEo.getUsername());
            message.setFrom(user);

        }
        if (targetEo != null) {
            User user = new User();
            user.setId(targetEo.getId());
            user.setUsername(StringUtils.isEmpty(targetEo.getName()) ? targetEo.getName() : targetEo.getUsername());
            message.setTo(user);
        }
        message.setMsg(msg);
        message.setSendDate(new Date());
        //未读状态
        message.setStatus(1);
        // 将消息保存到MongoDB
        message = this.messageDAO.saveMessage(message);

        String msgJson = MAPPER.writeValueAsString(message);

        // 判断to用户是否在线
        WebSocketSession toSession = SESSIONS.get(toId);
        if (toSession != null && toSession.isOpen()) {
            //TODO 具体格式需要和前端对接
            toSession.sendMessage(new TextMessage(msgJson));
            // 更新消息状态为已读
            this.messageDAO.updateMessageState(message.getId(), 2);
        }

    }

//    @Override
//    public void onMessage(String msg) {
//        try {
//            JsonNode jsonNode = MAPPER.readTree(msg);
//            long toId = jsonNode.get("to").get("id").longValue();
//
//            // 判断to用户是否在线
//            WebSocketSession toSession = SESSIONS.get(toId);
//            if (toSession != null && toSession.isOpen()) {
//                //TODO 具体格式需要和前端对接
//                toSession.sendMessage(new TextMessage(msg));
//                // 更新消息状态为已读
//                this.messageDAO.updateMessageState(new ObjectId(jsonNode.get("id").asText()), 2);
//            }else{
//                // 不需要做处理
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
}
