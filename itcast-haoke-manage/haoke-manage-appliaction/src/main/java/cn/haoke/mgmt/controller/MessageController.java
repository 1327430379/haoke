package cn.haoke.mgmt.controller;

import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.controller.base.AbstractBaseController;
import cn.haoke.mgmt.service.MessageService;
import cn.haoke.mgmt.util.SystemUserLoginInfoVo;
import cn.haoke.mgmt.util.SystemUserLoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("message")
public class MessageController extends AbstractBaseController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/list")
    public RestResponse listMessage(@RequestParam(value = "readStatus",required = false)Integer readStatus,
                                    @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                                    @RequestParam(value = "pageSize",required = false,defaultValue = "20")Integer pageSize){

        SystemUserLoginInfoVo loginInfo = SystemUserLoginUtil.getLoginInfo();
        Long userId = loginInfo.getUserId();
        return new RestResponse(messageService.listMessage(userId,readStatus,pageNum,pageSize));
    }

    @PostMapping("/status/read")
    public RestResponse changeReadStatus(@RequestParam("messageId")Long messageId){
        return messageService.changeReadStatusByMessageId(messageId,1);
    }
}
