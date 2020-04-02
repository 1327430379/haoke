package cn.haoke.mgmt.controller;

import cn.haoke.center.house.pojo.SeeHouseRequestEo;
import cn.haoke.common.vo.PageInfo;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.controller.base.AbstractBaseController;
import cn.haoke.mgmt.service.SeeHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("see/house")
public class SeeHouseController  extends AbstractBaseController {


    @Autowired
    private SeeHouseService seeHouseService;

    /***
     * 保存看房请求
     * @param requestEo
     * @return
     */
    @PostMapping("/update")
    public RestResponse<Void> saveRequest(@RequestBody SeeHouseRequestEo requestEo){
        return seeHouseService.saveRequest(requestEo);
    }

    @PutMapping("/updateRequestStatus")
    public RestResponse<Void> updateRequestStatus(@RequestParam("seeHouseStatus")Integer seeHouseStatus,
                                            @RequestParam("id")Long id){
        return seeHouseService.updateRequestStatus(id,seeHouseStatus);
    }
    @PostMapping("/add")
    public RestResponse<Long> addSeeHouseRequest(@RequestBody SeeHouseRequestEo eo){
        return seeHouseService.addSeeHouseRequest(eo);
    }
}