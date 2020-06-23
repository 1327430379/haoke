package cn.haoke.mgmt.controller;

import cn.haoke.center.house.dto.SeeHouseReqDto;
import cn.haoke.center.house.pojo.SeeHouseRequestEo;
import cn.haoke.center.house.vo.SeeHouseRecordVo;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.controller.base.AbstractBaseController;
import cn.haoke.mgmt.service.SeeHouseService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("see/house")
public class SeeHouseController  extends AbstractBaseController {


    @Autowired
    private SeeHouseService seeHouseService;


    @GetMapping("/{id}")
    public RestResponse<SeeHouseRecordVo> queryById(@PathVariable Long id){
        return seeHouseService.queryById(id);
    }

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
    @GetMapping("/list")
    public RestResponse<PageInfo> listByPage(@RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                                             @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize,
                                             SeeHouseReqDto dto){
        return seeHouseService.listByPage(pageNum,pageSize,dto);
    }


}
