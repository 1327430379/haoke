package cn.haoke.mgmt.service;

import cn.haoke.center.user.api.ApiIdentityRequestService;
import cn.haoke.center.user.api.ApiUserService;
import cn.haoke.center.user.constants.enums.RoleEnum;
import cn.haoke.center.user.pojo.IdentityRequestEo;
import cn.haoke.center.user.pojo.UserEo;
import cn.haoke.common.exception.BusinessException;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.util.SystemUserLoginInfoVo;
import cn.haoke.mgmt.util.SystemUserLoginUtil;
import cn.haoke.mgmt.util.TableResultUtils;
import cn.haoke.mgmt.vo.TableResult;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service
public class IdentityRequestService {

    @Reference(version = "1.0.0")
    private ApiIdentityRequestService apiIdentityRequestService;
    @Reference(version = "1.0.0")
    private ApiUserService apiUserService;
    public RestResponse<Long> addIdentityRequest(IdentityRequestEo eo){
        SystemUserLoginInfoVo loginInfo = SystemUserLoginUtil.getLoginInfo();
        if(loginInfo==null||loginInfo.getUserId()==null){
            throw new BusinessException("登录信息失效！");
        }
        eo.setUserId(loginInfo.getUserId());
        Long aLong = apiIdentityRequestService.addIdentityRequest(eo);
        return new RestResponse<>(aLong);
    }

    public RestResponse<TableResult> queryByPage(Integer pageNum,Integer pageSize,IdentityRequestEo eo){
        PageInfo<IdentityRequestEo> pageInfo = apiIdentityRequestService.queryByPage(pageNum, pageSize, eo);
        TableResult result = TableResultUtils.pageInfo2TableResult(pageInfo);
        return new RestResponse<>(result);
    }

    public RestResponse<Void> updateIdentityStatus(Long id,Integer identityStatus){
        IdentityRequestEo requestEo = apiIdentityRequestService.queryById(id);
        apiIdentityRequestService.updateStatus(id,identityStatus);
        if(identityStatus==1){
            UserEo userEo = new UserEo();
            userEo.setId(requestEo.getUserId());
            userEo.setRole(RoleEnum.HOUSE_OWNER.getRole());
            apiUserService.saveUser(userEo);
        }

        return RestResponse.VOID;

    }
}
