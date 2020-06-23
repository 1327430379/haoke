package cn.haoke.mgmt.service;

import cn.haoke.center.house.api.ApiNoticeMessageService;
import cn.haoke.center.house.api.ApiSeeHouseService;
import cn.haoke.center.house.constants.enums.MessageContentTemplate;
import cn.haoke.center.house.constants.enums.MessageTitleEnum;
import cn.haoke.center.house.constants.enums.SeeHouseStatus;
import cn.haoke.center.house.constants.enums.TargetUserType;
import cn.haoke.center.house.pojo.NoticeMessageEo;
import cn.haoke.center.house.pojo.SeeHouseRequestEo;
import cn.haoke.center.user.constants.enums.MessageStatus;
import cn.haoke.center.user.constants.enums.RoleEnum;
import cn.haoke.common.exception.BusinessException;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.util.SystemUserLoginInfoVo;
import cn.haoke.mgmt.util.SystemUserLoginUtil;
import com.alibaba.fastjson.JSON;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NoticeMessageService {

    @Reference(version = "1.0.0")
    private ApiNoticeMessageService apiNoticeMessageService;
    @Reference(version = "1.0.0")
    private ApiSeeHouseService apiSeeHouseService;

    private static Logger logger = LoggerFactory.getLogger(NoticeMessageService.class);

    public RestResponse<List<NoticeMessageEo>> queryNoticeMessageList(){
        SystemUserLoginInfoVo loginInfo = SystemUserLoginUtil.getLoginInfo();
        if(loginInfo==null){
            throw new BusinessException("登录信息失效，请重新登录~！");


        }
        List<NoticeMessageEo> noticeMessageEos = apiNoticeMessageService.queryByUserId(loginInfo.getUserId()).getData();
        if(CollectionUtils.isEmpty(noticeMessageEos)){
            return new RestResponse<>(new ArrayList<>());
        }
        return new RestResponse<>(noticeMessageEos);

    };
    public RestResponse<Void> updateNoticeMessage(NoticeMessageEo eo){
      return   apiNoticeMessageService.saveNoticeMessage(eo);
    }
    public RestResponse<Void> updateReadStatus(Long id,Integer readStatus){
        NoticeMessageEo eo = new NoticeMessageEo();
        eo.setId(id);
        eo.setReadStatus(readStatus);
        return apiNoticeMessageService.saveNoticeMessage(eo);
    }

    public RestResponse<Void> updateSeeHouseStatus(Long id, Integer seeHouseStatus) {
        SystemUserLoginInfoVo loginInfo = SystemUserLoginUtil.getLoginInfo();
        if (loginInfo.getUserId() == null) {
            throw new BusinessException("登录信息已失效，请重新登录！");
        }
        //1、查找到此条通知消息
        NoticeMessageEo noticeMessageEo = apiNoticeMessageService.queryById(id).getData();
        //2、查找此条消息的请求看房信息
        SeeHouseRequestEo data = apiSeeHouseService.queryById(noticeMessageEo.getDataId()).getData();
        if(data==null){
            throw new BusinessException("此条请求信息不存在");
        }
        logger.info("看房请求数据：{}", JSON.toJSONString(data));
        //3、更新看房请求中的看房请求状态
        apiSeeHouseService.updateRequestStatus(data.getId(), seeHouseStatus);
        //4、更新通知消息中的看房状态
        apiNoticeMessageService.updateSeeHouseStatus(id,seeHouseStatus);

        //还需发送通知消息
        String content = null;
        String title = null;
        if(SeeHouseStatus.WAIT_SEE_HOUSE.getStatus().equals(seeHouseStatus)){
            //同意看房
            content = String.format(MessageContentTemplate.SEE_HOUSE_NOTICE_PASS,loginInfo.getName(),data.getHouseName());
            title = MessageTitleEnum.SEE_HOUSE_NOTICE.getTitle();
        }else if(SeeHouseStatus.CANCELED.getStatus().equals(seeHouseStatus)){
            //用户取消看房
            content = String.format(MessageContentTemplate.USER_CANCEL_SEE_HOUSE,loginInfo.getName(),data.getSeeHouseTime(),data.getHouseName());
            title = MessageTitleEnum.CANCEL_SEE_HOUSE.getTitle();
        }else if(SeeHouseStatus.REFUSE_SEE_HOUSE.getStatus().equals(seeHouseStatus)){
            //房东拒绝看房
            content = String.format(MessageContentTemplate.SEE_HOUSE_NOTICE_NO_PASS,data.getSeeHouseTime(),data.getHouseName());
            title = MessageTitleEnum.CANCEL_SEE_HOUSE.getTitle();
        }


        //发送通知消息
        NoticeMessageEo messageEo = new NoticeMessageEo();
        messageEo.setTitle(title);
        messageEo.setReadStatus(MessageStatus.UN_READ.getStatus());
        messageEo.setSenderId(loginInfo.getUserId());
        if(RoleEnum.HOUSE_OWNER.getRole().equals(loginInfo.getRole())){
            logger.info("房东给租户发送通知,当前角色：{}",loginInfo.getRole());
            //发送给租户
            messageEo.setUserId(data.getTenantId());
            messageEo.setTargetType(TargetUserType.USER.getType());
        }else if(RoleEnum.USER.getRole().equals(loginInfo.getRole())){
            logger.info("租户给房东发送通知,当前角色：{}",loginInfo.getRole());
            //租户发送给房东
            messageEo.setUserId(data.getHouseOwnerId());
            messageEo.setTargetType(TargetUserType.HOUSE_OWNER.getType());
        }
        messageEo.setDataId(data.getId());
        messageEo.setCreateTime(new Date());
        messageEo.setUpdateTime(new Date());
        messageEo.setContent(content);

        messageEo.setSeeHouseStatus(seeHouseStatus);
        apiNoticeMessageService.addNoticeMessage(messageEo);
        return RestResponse.VOID;
    }
}
