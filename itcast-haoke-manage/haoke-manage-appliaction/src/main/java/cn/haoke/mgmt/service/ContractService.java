package cn.haoke.mgmt.service;

import cn.haoke.center.house.api.ApiContractService;
import cn.haoke.center.house.dto.ContractReqDto;
import cn.haoke.common.vo.RestResponse;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractService {

    @Reference(version = "1.0.0")
    private ApiContractService apiContractService;

    public RestResponse listByPage(ContractReqDto dto) {
        return new RestResponse(apiContractService.listByPage(dto));
    }
}
