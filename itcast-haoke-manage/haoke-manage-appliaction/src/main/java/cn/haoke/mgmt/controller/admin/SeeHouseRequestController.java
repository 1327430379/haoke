package cn.haoke.mgmt.controller.admin;

import cn.haoke.center.house.dto.SeeHouseReqDto;
import cn.haoke.center.house.vo.SeeHouseRecordVo;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.controller.base.AbstractBaseController;
import cn.haoke.mgmt.service.SeeHouseService;
import cn.haoke.mgmt.vo.TableResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/see/house")
public class SeeHouseRequestController extends AbstractBaseController {

    @Autowired
    private SeeHouseService seeHouseService;

    @GetMapping("/list")
    public RestResponse<TableResult> listByPage(SeeHouseReqDto dto,
                                                @RequestParam(name = "currentPage",required = false,
                                                        defaultValue = "1") Integer currentPage,
                                                @RequestParam(name = "pageSize",required = false,
                                                        defaultValue = "10") Integer pageSize){
        return seeHouseService.queryByPage(dto,currentPage,pageSize);
    }

    @GetMapping("/{id}")
    public RestResponse<SeeHouseRecordVo> queryById(@PathVariable Long id){
        return seeHouseService.queryById(id);
    }
    @DeleteMapping("/{id}")
    public RestResponse<Void> deleteSeeHouseRequest(@PathVariable Long id){
        return seeHouseService.deleteSeeHouseRequest(id);
    }
}
