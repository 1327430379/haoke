package cn.haoke.mgmt.controller;

import cn.haoke.center.house.dto.ContractReqDto;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.controller.base.AbstractBaseController;
import cn.haoke.mgmt.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("contract")
public class ContractController extends AbstractBaseController {

    @Autowired
    private ContractService contractService;

    @PostMapping("/listByPage")
    public RestResponse listPage(@RequestBody ContractReqDto dto) {
        return contractService.listByPage(dto);
    }
}
