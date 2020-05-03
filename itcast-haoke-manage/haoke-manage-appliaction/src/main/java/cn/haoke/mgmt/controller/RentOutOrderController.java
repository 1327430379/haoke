package cn.haoke.mgmt.controller;

import cn.haoke.center.house.dto.RentOutOrderDto;
import cn.haoke.center.house.pojo.RentOutOrderEo;
import cn.haoke.center.house.vo.RentOutOrderVo;
import cn.haoke.common.constants.CommonConstant;
import cn.haoke.common.exception.BusinessException;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.controller.base.AbstractBaseController;
import cn.haoke.mgmt.service.RentOutOrderService;
import cn.haoke.mgmt.util.SystemUserLoginInfoVo;
import cn.haoke.mgmt.util.SystemUserLoginUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rent/out/order")
public class RentOutOrderController extends AbstractBaseController {
    @Autowired
    private RentOutOrderService rentOutOrderService;

    @PostMapping
    public RestResponse<Long> createOrder(@RequestBody RentOutOrderEo eo) {
        SystemUserLoginInfoVo loginInfo = SystemUserLoginUtil.getLoginInfo();
        if (loginInfo == null) {
            throw new BusinessException(CommonConstant.LOGIN_SESSION_DISABLES);
        }
        eo.setTenantId(loginInfo.getUserId());
        return rentOutOrderService.addOrder(eo);
    }
    @PutMapping("/updateStatus")
    public RestResponse<Void> updateStatus(@RequestParam("id")Long id,
                                           @RequestParam("status")Integer status){
        RentOutOrderEo eo = new RentOutOrderEo();
        eo.setId(id);
        eo.setStatus(status);
        return rentOutOrderService.updateOrder(eo);
    }
    @GetMapping("/detail/{id}")
    public RestResponse<Void> getOrderDetail(@PathVariable Long id){
        return null;
    }

    @GetMapping("/list")
    public RestResponse<PageInfo<RentOutOrderVo>> listByPage(@RequestParam(value = "houseId",required = false)String houseId,
                                                             @RequestParam(value = "tenantId",required = false)Long tenantId,
                                                             @RequestParam(value = "houseOwnerId",required = false)Long houseOwnerId,
                                                             @RequestParam(value = "status",required = false)Integer status,
                                                             @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                                                             @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize){
        RentOutOrderDto dto = new RentOutOrderDto();
        dto.setHouseId(houseId);
        dto.setTenantId(tenantId);
        dto.setHouseOwnerId(houseOwnerId);
        dto.setStatus(status);
        dto.setPageNum(pageNum);
        dto.setPageSize(pageSize);
        return rentOutOrderService.listByPage(dto);

    }
}
