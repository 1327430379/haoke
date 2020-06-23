package cn.haoke.mgmt.service;

import cn.haoke.center.house.api.ApiRentOutOrderService;
import cn.haoke.center.house.dto.RentOutOrderDto;
import cn.haoke.center.house.pojo.RentOutOrderEo;
import cn.haoke.center.house.vo.RentOutOrderVo;
import cn.haoke.common.vo.RestResponse;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service
public class RentOutOrderService {

    @Reference(version = "1.0.0")
    private ApiRentOutOrderService apiRentOutOrderService;

    public RestResponse<Long> addOrder(RentOutOrderEo eo){
        return apiRentOutOrderService.addOrder(eo);
    }
    public RestResponse<Void> updateOrder(RentOutOrderEo eo){
        return apiRentOutOrderService.updateOrder(eo);
    }

    public RestResponse<PageInfo<RentOutOrderVo>> listByPage(RentOutOrderDto dto){
        return apiRentOutOrderService.listByPage(dto);
    }
}
