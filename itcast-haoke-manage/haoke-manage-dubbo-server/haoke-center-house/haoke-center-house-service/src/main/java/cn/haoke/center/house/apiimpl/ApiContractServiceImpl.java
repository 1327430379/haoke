package cn.haoke.center.house.apiimpl;

import cn.haoke.center.house.api.ApiContractService;
import cn.haoke.center.house.dto.ContractReqDto;
import cn.haoke.center.house.mapper.ContractMapper;
import cn.haoke.center.house.pojo.ContractEo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(version = "1.0.0")
public class ApiContractServiceImpl implements ApiContractService {

    @Autowired
    private ContractMapper contractMapper;

    @Override
    public PageInfo<ContractEo> listByPage(ContractReqDto dto) {
        if(dto.getPageNum()!=null && dto.getPageSize()!=null) {
            PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        }
        ContractEo eo = new ContractEo();
        eo.setContractStatus(dto.getContractStatus());
        QueryWrapper<ContractEo> wrapper = new QueryWrapper<>(eo);
        if(StringUtils.isNotEmpty(dto.getCustomerName())){
            wrapper.like("customer_name","%"+dto.getCustomerName()+"%");
        }
        if(StringUtils.isNotEmpty(dto.getOwnerName())){
            wrapper.like("owner_name","%"+dto.getOwnerName()+"%");
        }
        if(dto.getStartTime()!=null){
            wrapper.ge("create_time",dto.getStartTime());
        }
        if(dto.getEndTime()!=null){
            wrapper.le("end_time",dto.getEndTime());
        }
        List<ContractEo> list = contractMapper.selectList(wrapper);
        return new PageInfo<>(list);
    }
}
