package cn.haoke.center.user.apiimpl;

import cn.haoke.center.user.api.ApiIdentityRequestService;
import cn.haoke.center.user.mapper.IdentityRequestMapper;
import cn.haoke.center.user.pojo.IdentityRequestEo;
import cn.haoke.common.utils.IdUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Service(version = "1.0.0")
public class ApiIdentityRequestServiceImpl implements ApiIdentityRequestService {
    @Autowired
    private IdentityRequestMapper identityRequestMapper;

    @Override
    public Long addIdentityRequest(IdentityRequestEo eo) {
        eo.setId(IdUtils.getLongId());
        Date date = new Date();
        eo.setCreateTime(date);
        eo.setUpdateTime(date);
        identityRequestMapper.insert(eo);
        return eo.getId();
    }
    @Override
    public PageInfo<IdentityRequestEo> queryByPage(Integer pageNum, Integer pageSize, IdentityRequestEo eo) {
        PageHelper.startPage(pageNum, pageSize);
        Wrapper<IdentityRequestEo> wrapper = new QueryWrapper<>(eo);
        List<IdentityRequestEo> identityRequestEos = identityRequestMapper.selectList(wrapper);
        return new PageInfo<>(identityRequestEos);
    }
    @Override
    public void updateStatus(Long id,Integer identity){
        IdentityRequestEo requestEo = new IdentityRequestEo();
        requestEo.setId(id);
        requestEo.setIdentityStatus(identity);
        identityRequestMapper.updateById(requestEo);
    }

    @Override
    public IdentityRequestEo queryById(Long id) {
        return identityRequestMapper.selectById(id);
    }
}
