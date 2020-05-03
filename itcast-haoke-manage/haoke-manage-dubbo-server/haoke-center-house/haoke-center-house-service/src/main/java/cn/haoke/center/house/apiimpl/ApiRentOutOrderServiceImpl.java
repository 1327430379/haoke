package cn.haoke.center.house.apiimpl;

import cn.haoke.center.house.api.ApiRentOutOrderService;
import cn.haoke.center.house.constants.enums.OrderStatus;
import cn.haoke.center.house.dto.RentOutOrderDto;
import cn.haoke.center.house.mapper.RentOutOrderMapper;
import cn.haoke.center.house.pojo.RentOutOrderEo;
import cn.haoke.center.house.vo.RentOutOrderVo;
import cn.haoke.common.exception.BusinessException;
import cn.haoke.common.utils.IdUtils;
import cn.haoke.common.vo.RestResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Service(version = "1.0.0")
public class ApiRentOutOrderServiceImpl implements ApiRentOutOrderService {
    @Autowired
    private RentOutOrderMapper rentOutOrderMapper;

    @Override
    public RestResponse<Long> addOrder(RentOutOrderEo eo) {
        Date date = new Date();
        eo.setId(IdUtils.getLongId());
        eo.setCreateTime(date);
        eo.setUpdateTime(date);
        rentOutOrderMapper.insert(eo);
        return new RestResponse<>(eo.getId());
    }

    public RestResponse<Void> updateOrder(RentOutOrderEo eo) {
        if(eo.getId()==null){
            throw new BusinessException("更新时主键不能为空！");
        }
        Date date = new Date();
        eo.setUpdateTime(date);
        if(OrderStatus.PAID.getStatus().equals(eo.getStatus())){
            //付款后需要更新交易时间
            eo.setTradingTime(date);
        }
        rentOutOrderMapper.updateById(eo);
        return RestResponse.VOID;
    }

    @Override
    public RestResponse<PageInfo<RentOutOrderVo>> listByPage(RentOutOrderDto dto) {
        PageHelper.startPage(dto.getPageNum(),dto.getPageSize());
        RentOutOrderEo eo = new RentOutOrderEo();
        BeanUtils.copyProperties(dto,eo);
        List<RentOutOrderVo> vos = rentOutOrderMapper.queryList(eo);
        PageInfo<RentOutOrderVo> pageInfo = new PageInfo<>(vos);
        return new RestResponse<>(pageInfo);
    }
}
