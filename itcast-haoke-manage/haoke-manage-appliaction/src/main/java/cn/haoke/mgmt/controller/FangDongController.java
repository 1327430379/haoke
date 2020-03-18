package cn.haoke.mgmt.controller;


import cn.haoke.center.house.dto.FangDongReqDto;
import cn.haoke.mgmt.controller.base.AbstractBaseController;
import cn.haoke.mgmt.service.FangDongService;
import cn.haoke.common.vo.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 房东
 */
@RestController
@RequestMapping("fangDong")
public class FangDongController extends AbstractBaseController {


    @Autowired
    private FangDongService fangDongService;
    /**
     * 查询房东列表
     * @param dto 请求参数
     * @return
     */
    @PostMapping
    public RestResponse list(@RequestBody FangDongReqDto dto) {
        return new RestResponse(fangDongService.queryPage(dto));
    }

    @GetMapping("/{id}")
    public RestResponse queryDetail(@PathVariable("id") Long id){
        return new RestResponse(fangDongService.queryFangDongDetail(id));
    }
}
