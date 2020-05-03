package cn.haoke.center.house.apiimpl;

import cn.haoke.center.house.api.ApiHouseResourcesService;
import cn.haoke.center.house.constants.enums.RentHouseStatus;
import cn.haoke.center.house.dto.HouseResourceReqDto;
import cn.haoke.center.house.mapper.HouseResourcesMapper;
import cn.haoke.center.house.service.HouseResourcesService;
import cn.haoke.center.house.pojo.HouseResources;
import cn.haoke.common.exception.BusinessException;
import cn.haoke.common.utils.IdUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service(version = "1.0.0")
public class ApiHouseResourcesServiceImpl implements ApiHouseResourcesService {


    @Autowired
    private HouseResourcesMapper houseResourcesMapper;

    @Override
    public int saveHouseResources(HouseResources houseResources) {
        // 添加校验或者是其他的一些逻辑
        if (StringUtils.isBlank(houseResources.getTitle())) {
            // 不符合要求
            return -1;
        }
        if (houseResources.getHouseOwnerId() == null) {
            throw new BusinessException("房东信息不能为空！");
        }
        //添加
        houseResources.setId(IdUtils.getRandomIdByUUID());
        Date date = new Date();
        houseResources.setCreateTime(date);
        houseResources.setUpdateTime(date);
        return houseResourcesMapper.insert(houseResources);
    }

    @Override
    public PageInfo<HouseResources> queryHouseResourcesList(Integer pageNum, Integer pageSize, HouseResourceReqDto queryCondition) {
        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }
        HouseResources houseResources = new HouseResources();
        BeanUtils.copyProperties(queryCondition, houseResources);
        QueryWrapper<HouseResources> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(queryCondition.getTitle())) {
            wrapper.like("title", "%" + queryCondition.getTitle() + "%");
            houseResources.setTitle(null);
        }
        if (StringUtils.isNotEmpty(queryCondition.getHouseType())) {
            wrapper.like("house_type", "%" + queryCondition.getHouseType() + "%");
            houseResources.setHouseType(null);
        }
        if (StringUtils.isNotEmpty(queryCondition.getHouseOwnerName())) {
            wrapper.like("house_owner_name", "%" + queryCondition.getHouseOwnerName() + "%");
            houseResources.setHouseOwnerName(null);
        }
        if (queryCondition.getRent1() != null && queryCondition.getRent2() != null) {
            wrapper.ge("rent",queryCondition.getRent1());
            wrapper.le("rent",queryCondition.getRent2());
        }else if(queryCondition.getRent1()!=null){
            wrapper.ge("rent",queryCondition.getRent1());
        }else if(queryCondition.getRent2()!=null){
            wrapper.le("rent",queryCondition.getRent2());
        }
        //todo order by 有bug
       // wrapper.orderByDesc("update_time");
        wrapper.setEntity(houseResources);
        List<HouseResources> list = houseResourcesMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<>();
        }
        return new PageInfo<>(list);
    }

    @Override
    public HouseResources queryHouseResourceById(String id) {
        return houseResourcesMapper.selectById(id);
    }

    @Override
    public void updateHouseResource(HouseResources houseResources) {
        houseResourcesMapper.updateById(houseResources);
    }

    @Override
    public Map queryByRentStatus(HouseResources houseResources) {

        Map<String, List<HouseResources>> resultMap = new HashMap<>();
        //查询已出租房源
        HouseResources queryEo = new HouseResources();
        queryEo.setHouseOwnerId(houseResources.getHouseOwnerId());
        queryEo.setRentStatus(RentHouseStatus.RENT_OUT.getStatus());
        Wrapper<HouseResources> wrapper = new QueryWrapper<>(queryEo);
        List<HouseResources> rentOutList = houseResourcesMapper.selectList(wrapper);
        if (CollectionUtils.isNotEmpty(rentOutList)) {
            resultMap.put("rentOut", rentOutList);
        }

        //查询未出租的房源
        queryEo.setRentStatus(RentHouseStatus.UN_RENT.getStatus());
        wrapper = new QueryWrapper<>(queryEo);
        List<HouseResources> noRentList = houseResourcesMapper.selectList(wrapper);
        if (CollectionUtils.isNotEmpty(noRentList)) {
            resultMap.put("noRent", noRentList);
        }

        return resultMap;
    }

    @Override
    public void deleteById(String id) {
        houseResourcesMapper.deleteById(id);
    }

}
