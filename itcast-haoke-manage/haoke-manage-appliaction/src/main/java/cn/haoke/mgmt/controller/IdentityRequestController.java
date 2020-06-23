package cn.haoke.mgmt.controller;

import cn.haoke.center.user.pojo.IdentityRequestEo;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.service.IdentityRequestService;
import cn.haoke.mgmt.vo.TableResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("identity")
public class IdentityRequestController {

    @Autowired
    private IdentityRequestService identityRequestService;

    @PostMapping
    public RestResponse<Long> addIdentityRequest(@RequestBody @Validated IdentityRequestEo eo){
        return identityRequestService.addIdentityRequest(eo);
    }

    @PutMapping("/updateStatus")
    public RestResponse<Void> updateIdentityStatus(@RequestParam("id")Long id,
                                                   @RequestParam("identityStatus")Integer identityStatus){
        return identityRequestService.updateIdentityStatus(id,identityStatus);
    }

    @GetMapping("/list")
    public RestResponse<TableResult> listByPage(@RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                                                @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize,IdentityRequestEo eo){
        return identityRequestService.queryByPage(pageNum,pageSize,eo);
    }
}
