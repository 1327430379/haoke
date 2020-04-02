package cn.haoke.mgmt.service;

import cn.haoke.center.house.api.ApiHouseResourcesService;
import cn.haoke.center.house.api.ApiNoticeMessageService;
import cn.haoke.center.house.api.ApiSeeHouseService;
import cn.haoke.center.house.constants.enums.MessageContentTemplate;
import cn.haoke.center.house.constants.enums.MessageTitleEnum;
import cn.haoke.center.house.constants.enums.SeeHouseStatus;
import cn.haoke.center.house.constants.enums.TargetUserType;
import cn.haoke.center.house.pojo.NoticeMessageEo;
import cn.haoke.center.house.pojo.SeeHouseRequestEo;
import cn.haoke.center.user.constants.enums.MessageStatus;
import cn.haoke.center.user.pojo.MessageEo;
import cn.haoke.common.exception.BusinessException;
import cn.haoke.common.utils.DateUtils;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.util.SystemUserLoginInfoVo;
import cn.haoke.mgmt.util.SystemUserLoginUtil;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class SeeHouseService {
    @Reference(version = "1.0.0")
    private ApiSeeHouseService apiSeeHouseService;

    @Reference(version = "1.0.0")
    private ApiHouseResourcesService apiHouseResourcesService;

    @Reference(version = "1.0.0")
    private ApiNoticeMessageService apiNoticeMessageService;


    /***
     * 更新信息
     * @param requestEo 请求实体
     * @return  RestResponse<Void>
     */
    public RestResponse<Void> saveRequest(SeeHouseRequestEo requestEo) {
        SystemUserLoginInfoVo loginInfo = SystemUserLoginUtil.getLoginInfo();
        if (loginInfo.getUserId() == null) {
            throw new BusinessException("登录信息已失效，请重新登录！");
        }
        Date date = new Date();
        requestEo.setTenantId(loginInfo.getUserId());
        requestEo.setTenantName(loginInfo.getName());
        requestEo.setCreateTime(date);
        requestEo.setUpdateTime(date);
        requestEo.setSeeHouseStatus(SeeHouseStatus.WAIT_CONFIRM.getStatus());
        apiSeeHouseService.saveRequest(requestEo);
        return new RestResponse<>();
    }

    /***
     * 更新请求状态
     * @param id id
     * @param seeHouseStatus 请求状态
     * @return RestResponse<Void>
     */
    public RestResponse<Void> updateRequestStatus(Long id, Integer seeHouseStatus) {
        SystemUserLoginInfoVo loginInfo = SystemUserLoginUtil.getLoginInfo();
        if (loginInfo.getUserId() == null) {
            throw new BusinessException("登录信息已失效，请重新登录！");
        }
        //查找此条请求信息
        SeeHouseRequestEo data = apiSeeHouseService.queryById(id).getData();
        if(data==null){
            throw new BusinessException("此条请求信息不存在");
        }
        apiSeeHouseService.updateRequestStatus(id, seeHouseStatus);

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
        messageEo.setUserId(data.getTenantId());
        messageEo.setDataId(data.getId());
        messageEo.setTargetType(TargetUserType.USER.getType());
        messageEo.setCreateTime(new Date());
        messageEo.setUpdateTime(new Date());
        messageEo.setContent(content);
        messageEo.setUserId(loginInfo.getUserId());
        messageEo.setSeeHouseStatus(seeHouseStatus);
        apiNoticeMessageService.addNoticeMessage(messageEo);
        return RestResponse.VOID;
    }

    /***
     * 添加看房请求
     * @param eo 请求对象
     * @return  RestResponse<Long>
     */
    @Transactional(rollbackFor = Exception.class)
    public RestResponse<Long> addSeeHouseRequest(SeeHouseRequestEo eo) {
        SystemUserLoginInfoVo loginInfo = SystemUserLoginUtil.getLoginInfo();
        if (loginInfo.getUserId() == null) {
            throw new BusinessException("登录信息已失效，请重新登录！");
        }
        eo.setTenantId(loginInfo.getUserId());
        eo.setTenantName(loginInfo.getName());
        eo.setTenantMobile(loginInfo.getMobile());
        Long dataId = apiSeeHouseService.saveRequest(eo).getData();

        //添加成功后需要发送通知信息给房东
        if(dataId==null){
            throw new BusinessException("添加出错！");
        }
        NoticeMessageEo messageEo = new NoticeMessageEo();
        Date date = new Date();
        messageEo.setDataId(dataId);
        messageEo.setSenderId(eo.getHouseOwnerId());
        messageEo.setUserId(loginInfo.getUserId());
        messageEo.setTargetType(TargetUserType.USER.getType());
        messageEo.setTitle(MessageTitleEnum.APPOINTMENT_APPLY.getTitle());
        messageEo.setSeeHouseStatus(SeeHouseStatus.WAIT_CONFIRM.getStatus());
        messageEo.setCreateTime(date);
        messageEo.setUpdateTime(date);
       //添加通知信息
        String content = String.format(MessageContentTemplate.APPOINTMENT_APPLY, loginInfo.getName(), eo.getSeeHouseTime(), eo.getHouseName());
        messageEo.setContent(content);
        apiNoticeMessageService.addNoticeMessage(messageEo);

        return new RestResponse<>(dataId);

    }

}
