package cn.haoke.im.controller;

import cn.haoke.common.vo.RestResponse;
import cn.haoke.im.pojo.Message;
import cn.haoke.im.pojo.SessionEo;
import cn.haoke.im.service.MessageService;
import cn.haoke.im.service.SessionService;
import cn.haoke.mgmt.controller.base.AbstractBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("imUserController")
@CrossOrigin
@RequestMapping("user")
public class UserController extends AbstractBaseController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private SessionService sessionService;

    //拉取用户列表（模拟实现）
    @GetMapping("/sessionList")
    public RestResponse<List<SessionEo>> queryUserSessionList(@RequestParam("fromId") Long
                                                                      fromId) {

        List<SessionEo> sessionEos = sessionService.getSessionList(fromId).getData();
        if (sessionEos.size() > 0) {
            for (SessionEo sessionEo : sessionEos) {
                //查询最后一条消息
                List<Message> messages = messageService.queryMessageList(sessionEo.getUserId(), sessionEo.getTargetId(), 1, 1);
                if (messages != null && !messages.isEmpty()) {
                    Message message = messages.get(0);
                    sessionEo.setLastMessage(message.getMsg());
                    sessionEo.setLastMessageTime(message.getSendDate());
                }
            }
        }

//        List<Map<String, Object>> result = new ArrayList<>();
//        for (Map.Entry<Long, User> userEntry : UserData.USER_MAP.entrySet()) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("id", userEntry.getValue().getId());
//            map.put("avatar", "http://itcast-haoke.oss-cn-qingdao.aliyuncs.com/images/2018/12/08/15442410962743524.jpg");
//            map.put("from_user", fromId);
//            map.put("info_type", null);
//            map.put("to_user", map.get("id"));
//            map.put("username", userEntry.getValue().getUsername());
//            // 获取最后一条消息 TODO 没有读取最新消息，而是读取了最早的那个消息
//            List<Message> messages = this.messageService.queryMessageList(fromId,
//                    userEntry.getValue().getId(), 1, 1);
//
//            if (messages != null && !messages.isEmpty()) {
//                Message message = messages.get(0);
//                map.put("chat_msg", message.getMsg());
//                map.put("chat_time", message.getSendDate().getTime());
//            }
//            result.add(map);
//        }
        return new RestResponse<>(sessionEos);
    }

    @PostMapping("/addSession")
    public RestResponse addSession(@RequestBody SessionEo sessionEo) {
        return sessionService.addSession(sessionEo);
    }

}
