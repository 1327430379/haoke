package cn.haoke.mgmt.service;

import cn.haoke.center.house.api.ApiNoticeMessageService;
import cn.haoke.center.house.api.ApiSeeHouseService;
import cn.haoke.center.house.pojo.NoticeMessageEo;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.util.SystemUserLoginInfoVo;
import cn.haoke.mgmt.util.SystemUserLoginUtil;
import cn.haoke.mgmt.vo.NoticeMessageVo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeMessageService {

    @Reference(version = "1.0.0")
    private ApiNoticeMessageService apiNoticeMessageService;


    public RestResponse<List<NoticeMessageEo>> queryNoticeMessageList(){
        SystemUserLoginInfoVo loginInfo = SystemUserLoginUtil.getLoginInfo();
        List<NoticeMessageEo> noticeMessageEos = apiNoticeMessageService.queryByUserId(loginInfo.getUserId()).getData();
        if(CollectionUtils.isEmpty(noticeMessageEos)){
            return new RestResponse<>(new ArrayList<>());
        }
        return new RestResponse<>(noticeMessageEos);

    };
}
