package cn.haoke.mgmt.service;

import cn.haoke.center.house.api.ApiHouseResourcesService;
import cn.haoke.center.house.api.ApiNoticeMessageService;
import cn.haoke.center.house.api.ApiSeeHouseService;
import cn.haoke.center.house.constants.enums.MessageContentTemplate;
import cn.haoke.center.house.constants.enums.MessageTitleEnum;
import cn.haoke.center.house.constants.enums.SeeHouseStatus;
import cn.haoke.center.house.constants.enums.TargetUserType;
import cn.haoke.center.house.dto.SeeHouseReqDto;
import cn.haoke.center.house.pojo.NoticeMessageEo;
import cn.haoke.center.house.pojo.SeeHouseRequestEo;
import cn.haoke.center.house.vo.SeeHouseRecordVo;
import cn.haoke.center.user.constants.enums.MessageStatus;
import cn.haoke.center.user.constants.enums.RoleEnum;
import cn.haoke.center.user.pojo.UserEo;
import cn.haoke.common.constants.CommonConstant;
import cn.haoke.common.constants.enums.DatePattern;
import cn.haoke.common.exception.BusinessException;
import cn.haoke.common.utils.DateUtils;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.util.SystemUserLoginInfoVo;
import cn.haoke.mgmt.util.SystemUserLoginUtil;
import cn.haoke.mgmt.util.TableResultUtils;
import cn.haoke.mgmt.vo.TableResult;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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

    private static Logger logger = LoggerFactory.getLogger(SeeHouseService.class);

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
        logger.info("看房请求数据：{}", JSON.toJSONString(data));
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
        eo.setSeeHouseStatus(SeeHouseStatus.WAIT_CONFIRM.getStatus());
        Long dataId = apiSeeHouseService.saveRequest(eo).getData();

        //添加成功后需要发送通知信息给房东
        if(dataId==null){
            throw new BusinessException("添加出错！");
        }
        NoticeMessageEo messageEo = new NoticeMessageEo();
        Date date = new Date();
        messageEo.setDataId(dataId);
        messageEo.setSenderId(loginInfo.getUserId());
        messageEo.setUserId(eo.getHouseOwnerId());
        messageEo.setTargetType(TargetUserType.USER.getType());
        messageEo.setTitle(MessageTitleEnum.APPOINTMENT_APPLY.getTitle());
        messageEo.setSeeHouseStatus(SeeHouseStatus.WAIT_CONFIRM.getStatus());
        messageEo.setCreateTime(date);
        messageEo.setUpdateTime(date);
       //添加通知信息
        String formatTime = DateUtils.format(eo.getSeeHouseTime(), DatePattern.DATETIME_PATTERN_HHMM.getPattern());
        String content = String.format(MessageContentTemplate.APPOINTMENT_APPLY, loginInfo.getName(), formatTime, eo.getHouseName());
        messageEo.setContent(content);
        apiNoticeMessageService.addNoticeMessage(messageEo);

        return new RestResponse<>(dataId);

    }

    /**
     * 分页查询看房请求
     * @param pageNum
     * @param pageSize
     * @return
     */
    public RestResponse<PageInfo> listByPage(Integer pageNum, Integer pageSize,SeeHouseReqDto dto){
        SystemUserLoginInfoVo loginInfo = SystemUserLoginUtil.getLoginInfo();
        if(loginInfo==null||loginInfo.getUserId()==null){
            throw new BusinessException(CommonConstant.LOGIN_SESSION_DISABLES);
        }

        SeeHouseRequestEo eo = new SeeHouseRequestEo();
        if(dto!=null){
            BeanUtils.copyProperties(dto,eo);
        }
        if(RoleEnum.USER.getRole().equals(loginInfo.getRole())){
            eo.setTenantId(loginInfo.getUserId());
        }else if(RoleEnum.HOUSE_OWNER.getRole().equals(loginInfo.getRole())){
            eo.setHouseOwnerId(loginInfo.getUserId());
        }
       return apiSeeHouseService.listByPage(pageNum,pageSize,eo);
    }

    /***
     * 后台表格查询
     * @return
     */
    public RestResponse<TableResult> queryByPage(SeeHouseReqDto dto,Integer pageNum,Integer pageSize){

        if(dto==null){
            dto = new SeeHouseReqDto();
        }
        UserEo userEo = SystemUserLoginUtil.getCurrentLoginInfo();
        if(userEo==null){
            throw new BusinessException("登录信息失效，请重新登录！");
        }
        if(!RoleEnum.ADMIN.equals(userEo.getRole())){
            dto.setHouseOwnerId(userEo.getId());
        }

        PageInfo<SeeHouseRequestEo> pageInfo = apiSeeHouseService.queryByPage(dto, pageNum, pageSize).getData();
        TableResult tableResult = TableResultUtils.pageInfo2TableResult(pageInfo);
        return new RestResponse<>(tableResult);
    }

    public RestResponse<SeeHouseRecordVo> queryById(Long id) {
        return apiSeeHouseService.queryDetailById(id);
    }

    public RestResponse<Void> deleteSeeHouseRequest(Long id) {
        apiSeeHouseService.deleteSeeHouseRequest(id);
        return RestResponse.VOID;
    }

    public RestResponse<SeeHouseRecordVo> queryByDataId(Long dataId) {
        return null;
    }
}
