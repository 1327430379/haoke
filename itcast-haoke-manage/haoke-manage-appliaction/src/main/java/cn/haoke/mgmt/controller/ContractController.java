package cn.haoke.mgmt.controller;

import cn.haoke.center.house.dto.ContractReqDto;
import cn.haoke.center.house.pojo.ContractDto;
import cn.haoke.center.house.pojo.ContractEo;
import cn.haoke.common.exception.BusinessException;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.controller.base.AbstractBaseController;
import cn.haoke.mgmt.service.ContractService;
import cn.haoke.mgmt.util.SystemUserLoginInfoVo;
import cn.haoke.mgmt.util.SystemUserLoginUtil;
import cn.haoke.mgmt.vo.TableResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("contract")
public class ContractController extends AbstractBaseController {

    @Autowired
    private ContractService contractService;

    @PostMapping("/listByPage")
    public RestResponse<TableResult> listPage(@RequestBody ContractReqDto dto) {
        return contractService.listByPage(dto);
    }

    @PostMapping("/add")
    public RestResponse<Long> addContract(@RequestBody @Validated ContractDto eo){
        SystemUserLoginInfoVo loginInfo = SystemUserLoginUtil.getLoginInfo();
        if(loginInfo==null){
            throw new BusinessException("登录信息失效，请重新登录！");
        }
        eo.setCustomerId(loginInfo.getUserId());
        eo.setCustomerMobile(loginInfo.getMobile());
        eo.setCustomerName(loginInfo.getName());
        ContractEo contractEo = new ContractEo();
        BeanUtils.copyProperties(eo,contractEo);
        return contractService.addContract(contractEo);
    }
    @GetMapping("/{id}")
    public RestResponse<ContractEo> queryById(@PathVariable Long id){
        return contractService.queryById(id);
    }

    @PutMapping("/updateStatus")
    public RestResponse<Void> updateStatus(@RequestParam("id")Long id,
                                            @RequestParam("contractStatus")Integer contractStatus){
        return contractService.updateContractStatus(id,contractStatus);
    }
}
