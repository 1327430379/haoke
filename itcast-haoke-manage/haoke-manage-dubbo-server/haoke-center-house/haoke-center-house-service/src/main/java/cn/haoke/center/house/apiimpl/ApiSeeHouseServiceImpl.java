package cn.haoke.center.house.apiimpl;

import cn.haoke.center.house.api.ApiSeeHouseService;
import cn.haoke.center.house.constants.enums.MessageContentTemplate;
import cn.haoke.center.house.constants.enums.RequestSesHouseStatus;
import cn.haoke.center.house.constants.enums.SeeHouseStatus;
import cn.haoke.center.house.dto.SeeHouseReqDto;
import cn.haoke.center.house.mapper.NoticeMessageMapper;
import cn.haoke.center.house.mapper.SeeHouseRequestMapper;
import cn.haoke.center.house.pojo.NoticeMessageEo;
import cn.haoke.center.house.pojo.SeeHouseRequestEo;
import cn.haoke.center.house.vo.SeeHouseRecordVo;
import cn.haoke.common.vo.RestResponse;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service(version = "1.0.0")
public class ApiSeeHouseServiceImpl implements ApiSeeHouseService {
    @Autowired
    private SeeHouseRequestMapper seeHouseRequestMapper;
    @Autowired
    private NoticeMessageMapper noticeMessageMapper;

    @Override
    public RestResponse<Long> addRequest(SeeHouseRequestEo requestEo) {
        Date date = new Date();
        requestEo.setCreateTime(date);
        requestEo.setUpdateTime(date);
        seeHouseRequestMapper.insert(requestEo);
        return new RestResponse<>(requestEo.getId());

    }

    @Override
    public RestResponse<Long> saveRequest(SeeHouseRequestEo requestEo) {
        Date date = new Date();
        if(requestEo.getId()==null){
            requestEo.setCreateTime(date);
            seeHouseRequestMapper.insert(requestEo);
        }else{
            requestEo.setUpdateTime(date);
            seeHouseRequestMapper.updateById(requestEo);
        }

        return new RestResponse<>(requestEo.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResponse<Void> updateRequestStatus(Long id, Integer seeHouseStatus) {
        SeeHouseRequestEo requestEo = new SeeHouseRequestEo();
        Date date = new Date();
        requestEo.setId(id);
        requestEo.setUpdateTime(date);
        requestEo.setSeeHouseStatus(seeHouseStatus);

        if(SeeHouseStatus.WAIT_SEE_HOUSE.getStatus().equals(seeHouseStatus)){
            //同意看房
            requestEo.setConfirmTime(date);
        }else if(SeeHouseStatus.FINISHED_HAS_SEE.getStatus().equals(seeHouseStatus)||
                SeeHouseStatus.FINISHED_NOT_SEE.getStatus().equals(seeHouseStatus)){
            //已完成看房（已看，未看）
            requestEo.setFinishedTime(date);
        }
        seeHouseRequestMapper.updateById(requestEo);
        return new RestResponse<>();
    }

    @Override
    public RestResponse<SeeHouseRequestEo> queryById(Long id) {
        return new RestResponse<>(seeHouseRequestMapper.selectById(id));
    }

    @Override
    public RestResponse<SeeHouseRecordVo> queryDetailById(Long id) {
        return new RestResponse<>(seeHouseRequestMapper.queryById(id));
    }

    @Override
    public RestResponse<PageInfo> listByPage(Integer pageNum, Integer pageSize, SeeHouseRequestEo eo) {
        PageHelper.startPage(pageNum,pageSize);
        List<SeeHouseRecordVo> vos = seeHouseRequestMapper.queryList(eo);
        PageInfo<SeeHouseRecordVo> pageInfo = new PageInfo<>(vos);
        return new RestResponse<>(pageInfo);
    }

    @Override
    public RestResponse<PageInfo<SeeHouseRequestEo>> queryByPage(SeeHouseReqDto dto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<SeeHouseRequestEo> wrapper =new QueryWrapper<>();
        SeeHouseRequestEo requestEo = new SeeHouseRequestEo();
        BeanUtils.copyProperties(dto,requestEo);
        if(StringUtils.isNotEmpty(dto.getHouseName())){
            wrapper.like("house_name","%"+dto.getHouseName()+"%");
            requestEo.setHouseName(null);
        }
        if(StringUtils.isNotEmpty(dto.getHouseOwnerName())){
            wrapper.like("house_owner_name","%"+dto.getHouseOwnerName()+"%");
            requestEo.setHouseOwnerName(null);
        }
        if(StringUtils.isNotEmpty(dto.getTenantName())){
            wrapper.like("tenant_name","%"+dto.getTenantName()+"%");
            requestEo.setTenantName(null);
        }
        wrapper.setEntity(requestEo);
        List<SeeHouseRequestEo> seeHouseRequestEos = seeHouseRequestMapper.selectList(wrapper);
        if(seeHouseRequestEos==null){
            seeHouseRequestEos = Collections.emptyList();
        }
        PageInfo<SeeHouseRequestEo> pageInfo = new PageInfo<>(seeHouseRequestEos);
        return new RestResponse<>(pageInfo);
    }

    @Override
    public RestResponse<Void> deleteSeeHouseRequest(Long id) {
        //先删除看房请求
        int result = seeHouseRequestMapper.deleteById(id);
        //删除关联的通知消息
        if(result>0){
            NoticeMessageEo messageEo = NoticeMessageEo.builder().dataId(id).build();
            Wrapper<NoticeMessageEo> wrapper =new QueryWrapper<>(messageEo);
            noticeMessageMapper.delete(wrapper);
        }
        return RestResponse.VOID;
    }
}
