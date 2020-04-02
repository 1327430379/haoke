package cn.haoke.mgmt.controller;

import cn.haoke.center.house.pojo.NoticeMessageEo;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.controller.base.AbstractBaseController;
import cn.haoke.mgmt.service.NoticeMessageService;
import cn.haoke.mgmt.vo.NoticeMessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("notice/message")
public class NoticeMessageController extends AbstractBaseController {

    @Autowired
    private NoticeMessageService noticeMessageService;

    @GetMapping
    public RestResponse<List<NoticeMessageEo>> queryNoticeMessageList(){
        return noticeMessageService.queryNoticeMessageList();
    }
}
