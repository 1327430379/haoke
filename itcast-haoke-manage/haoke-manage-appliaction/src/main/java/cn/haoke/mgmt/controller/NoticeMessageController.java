package cn.haoke.mgmt.controller;

import cn.haoke.center.house.pojo.NoticeMessageEo;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.controller.base.AbstractBaseController;
import cn.haoke.mgmt.service.NoticeMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 改变消息已读状态
     * @param id
     * @param readStatus
     * @return
     */
    @PutMapping
    public RestResponse<Void> updateReadStatus(@RequestParam("id")Long id,
                                               @RequestParam(value = "readStatus",required = false,defaultValue = "1")Integer readStatus){
        return noticeMessageService.updateReadStatus(id,readStatus);
    }

    /**
     * 更新看房状态
     */
    @PutMapping("/updateStatus")
    public RestResponse<Void> updateSeeHouseStatus(@RequestParam("id") Long id,
                                                   @RequestParam("seeHouseStatus")Integer seeHouseStatus){
        return noticeMessageService.updateSeeHouseStatus(id,seeHouseStatus);
    }

}
