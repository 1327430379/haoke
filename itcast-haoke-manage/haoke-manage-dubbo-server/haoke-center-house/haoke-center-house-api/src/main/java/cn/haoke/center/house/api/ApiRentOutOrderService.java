package cn.haoke.center.house.api;

import cn.haoke.center.house.dto.RentOutOrderDto;
import cn.haoke.center.house.pojo.RentOutOrderEo;
import cn.haoke.center.house.vo.RentOutOrderVo;
import cn.haoke.common.vo.RestResponse;
import com.github.pagehelper.PageInfo;

/***
 * 租赁订单接口
 */
public interface ApiRentOutOrderService {

    /***
     * 添加订单
     * @param eo 实体对象
     * @return Long
     */
    RestResponse<Long> addOrder(RentOutOrderEo eo);

    /***
     * 更新订单
     * @param eo
     * @return
     */
    RestResponse<Void> updateOrder(RentOutOrderEo eo);

    /**
     * 分页查询
     * @param dto 请求参数
     * @return PageInfo
     */
    RestResponse<PageInfo<RentOutOrderVo>> listByPage(RentOutOrderDto dto);



}
