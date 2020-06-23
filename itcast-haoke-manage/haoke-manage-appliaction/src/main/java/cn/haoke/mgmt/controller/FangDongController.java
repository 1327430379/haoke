package cn.haoke.mgmt.controller;


import cn.haoke.center.house.dto.FangDongReqDto;
import cn.haoke.center.user.dto.UserSearchDto;
import cn.haoke.center.user.pojo.UserEo;
import cn.haoke.mgmt.controller.base.AbstractBaseController;
import cn.haoke.mgmt.service.FangDongService;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.vo.TableResult;
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
    @GetMapping("/list")
    public RestResponse<TableResult> listByPage(@RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                                                @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize,
                                                UserSearchDto dto) {
        if(dto==null){
            dto = new UserSearchDto();
        }
        dto.setPageNum(pageNum);
        dto.setPageSize(pageSize);
        return fangDongService.queryPage(dto);
    }

    @GetMapping("/{id}")
    public RestResponse<UserEo> queryDetail(@PathVariable("id") Long id){
       return fangDongService.queryFangDongDetail(id);
    }
}
