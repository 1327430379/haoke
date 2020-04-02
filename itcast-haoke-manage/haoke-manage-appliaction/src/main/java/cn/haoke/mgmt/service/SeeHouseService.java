package cn.haoke.mgmt.service;

import cn.haoke.center.house.api.ApiHouseResourcesService;
import cn.haoke.center.house.api.ApiSeeHouseService;
import cn.haoke.center.house.constants.enums.SeeHouseStatus;
import cn.haoke.center.house.pojo.SeeHouseRequestEo;
import cn.haoke.center.user.constants.enums.MessageStatus;
import cn.haoke.center.user.pojo.MessageEo;
import cn.haoke.common.exception.BusinessException;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.util.SystemUserLoginInfoVo;
import cn.haoke.mgmt.util.SystemUserLoginUtil;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SeeHouseService {
    @Reference(version = "1.0.0")
    private ApiSeeHouseService apiSeeHouseService;

    @Reference(version = "1.0.0")
    private ApiHouseResourcesService apiHouseResourcesService;

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
        requestEo.setRequestStatus(SeeHouseStatus.WAIT_CONFIRM.getStatus());
        apiSeeHouseService.saveRequest(requestEo);
        return new RestResponse<>();
    }

    /***
     * 更新请求状态
     * @param id id
     * @param requestStatus 请求状态
     * @return RestResponse<Void>
     */
    public RestResponse<Void> updateRequestStatus(Long id, Integer requestStatus) {
        SystemUserLoginInfoVo loginInfo = SystemUserLoginUtil.getLoginInfo();
        apiSeeHouseService.updateRequestStatus(id, requestStatus);

        //查到房源信息
        SeeHouseRequestEo houseRequestEo = apiSeeHouseService.queryById(id).getData();

        //发送异步消息
        MessageEo messageEo = new MessageEo();
        messageEo.setTitle("预约申请");
        messageEo.setReadStatus(MessageStatus.UN_READ.getStatus());
        messageEo.setSenderId(loginInfo.getUserId());
        messageEo.setUserId(houseRequestEo.getTenantId());
        messageEo.setDataId(houseRequestEo.getId());
        messageEo.setType(1);
        messageEo.setCreateTime(new Date());
        messageEo.setUpdateTime(new Date());
        if (SeeHouseStatus.WAIT_SEE_HOUSE.getStatus().equals(requestStatus)) {
            messageEo.setContent("您预约的" + houseRequestEo.getHouseName() + "的房源的通知消息 ");
        }
        messageEo.setUserId(loginInfo.getUserId());
        messageEo.setReadStatus(0);
        messageEo.setContent("");
        return RestResponse.VOID;
    }

    /***
     * 添加看房请求
     * @param eo 请求对象
     * @return  RestResponse<Long>
     */
    public RestResponse<Long> addSeeHouseRequest(SeeHouseRequestEo eo) {
        SystemUserLoginInfoVo loginInfo = SystemUserLoginUtil.getLoginInfo();
        if (loginInfo.getUserId() == null) {
            throw new BusinessException("登录信息已失效，请重新登录！");
        }
        Date date = new Date();
        eo.setTenantId(loginInfo.getUserId());
        eo.setTenantName(loginInfo.getName());
        eo.setCreateTime(date);
        eo.setUpdateTime(date);
        apiSeeHouseService.saveRequest(eo);

    }
}
