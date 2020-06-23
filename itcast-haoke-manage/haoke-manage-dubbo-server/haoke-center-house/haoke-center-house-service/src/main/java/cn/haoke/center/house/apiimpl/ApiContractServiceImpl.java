package cn.haoke.center.house.apiimpl;

import cn.haoke.center.house.api.ApiContractService;
import cn.haoke.center.house.constants.CustomeConstant;
import cn.haoke.center.house.constants.enums.MessageContentTemplate;
import cn.haoke.center.house.dto.ContractReqDto;
import cn.haoke.center.house.mapper.ContractMapper;
import cn.haoke.center.house.pojo.ContractEo;
import cn.haoke.common.constants.enums.DatePattern;
import cn.haoke.common.utils.CodeUtils;
import cn.haoke.common.utils.DateUtils;
import cn.haoke.common.utils.IdUtils;
import com.aliyun.oss.common.utils.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
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

    @Override
    public void addContract(ContractEo eo) {
        eo.setContractCode(CodeUtils.generateCode(CustomeConstant.CONTRACT_CODE_PREFIX));
        Date date = new Date();
        eo.setCreateTime(date);
        eo.setUpdateTime(date);
        String startTime = DateUtils.format(date, DatePattern.DATE_PATTERN.getPattern());
        String endTime = DateUtils.format(eo.getEndTime(), DatePattern.DATE_PATTERN.getPattern());
        String content = String.format(MessageContentTemplate.CONTRACT_CONTENT_TEMPLATE, startTime, endTime);
        eo.setContractContent(content);
        eo.setId(IdUtils.getLongId());
        contractMapper.insert(eo);
    }

    @Override
    public ContractEo queryById(Long id) {
        return contractMapper.selectById(id);
    }

    @Override
    public List<ContractEo> queryByCondition(ContractEo eo) {
        QueryWrapper<ContractEo> wrapper = new QueryWrapper<>(eo);
        return contractMapper.selectList(wrapper);
    }

    @Override
    public void updateContractStatus(Long id, Integer contractStatus) {
        ContractEo eo = new ContractEo();
        eo.setId(id);
        eo.setContractStatus(contractStatus);
        contractMapper.updateById(eo);
    }
}
