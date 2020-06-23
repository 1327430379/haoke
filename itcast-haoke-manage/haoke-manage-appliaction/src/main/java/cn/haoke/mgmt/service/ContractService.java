package cn.haoke.mgmt.service;

import cn.haoke.center.house.api.ApiContractService;
import cn.haoke.center.house.dto.ContractReqDto;
import cn.haoke.center.house.pojo.ContractEo;
import cn.haoke.common.exception.BusinessException;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.util.TableResultUtils;
import cn.haoke.mgmt.vo.TableResult;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.lucene.util.CollectionUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ContractService {

    @Reference(version = "1.0.0")
    private ApiContractService apiContractService;

    public RestResponse<TableResult> listByPage(ContractReqDto dto) {
        PageInfo<ContractEo> pageInfo = apiContractService.listByPage(dto);
        TableResult tableResult = TableResultUtils.pageInfo2TableResult(pageInfo);
        return new RestResponse<>(tableResult);
    }

    public RestResponse<Long> addContract(ContractEo eo) {
        //查询是否存在合同信息
        ContractEo queryEo = new ContractEo();
        queryEo.setHouseId(eo.getHouseId());
        queryEo.setCustomerId(eo.getCustomerId());
        List<ContractEo> contractEos = apiContractService.queryByCondition(queryEo);
        if(!CollectionUtils.isEmpty(contractEos)){
            throw new BusinessException("合同信息已存在，请勿重复提交！");
        }
        apiContractService.addContract(eo);
        return new RestResponse<>(eo.getId());
    }

    public RestResponse<ContractEo> queryById(Long id) {
        return new RestResponse<>(apiContractService.queryById(id));
    }

    public RestResponse<Void> updateContractStatus(Long id,Integer contractStatus){
        apiContractService.updateContractStatus(id,contractStatus);
        return RestResponse.VOID;
    }
}
