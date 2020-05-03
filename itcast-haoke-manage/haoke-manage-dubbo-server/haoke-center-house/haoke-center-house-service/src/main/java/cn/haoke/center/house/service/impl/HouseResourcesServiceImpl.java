package cn.haoke.center.house.service.impl;

import cn.haoke.center.house.mapper.HouseResourcesMapper;
import cn.haoke.common.exception.BusinessException;
import cn.haoke.common.service.BaseServiceImpl;
import cn.haoke.center.house.pojo.HouseResources;
import cn.haoke.center.house.service.HouseResourcesService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class HouseResourcesServiceImpl implements HouseResourcesService {

    @Autowired
    private HouseResourcesMapper houseResourcesMapper;

    /**
     * @param houseResources
     * @return -1:输入的参数不符合要求，0：数据插入数据库失败，1：成功
     */
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
        return houseResourcesMapper.insert(houseResources);
    }

    @Override
    public PageInfo<HouseResources> queryHouseResourcesList(Integer pageNum, Integer pageSize, HouseResources queryCondition) {

        if (pageNum != null && pageSize != null) {
            PageHelper.startPage(pageNum, pageSize);
        }

        QueryWrapper<HouseResources> queryWrapper = new QueryWrapper();

        // 根据数据的更新时间做倒序排序
        queryWrapper.orderByDesc("update_time");
        List<HouseResources> list = houseResourcesMapper.selectList(queryWrapper);
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
    public boolean updateHouseResource(HouseResources houseResources) {
        return houseResourcesMapper.updateById(houseResources) == 1;
    }

    @Override
    public List<HouseResources> queryByFangDongId(Long fangDongId) {
        // 根据数据的更新时间做倒序排序
        HouseResources houseResources = new HouseResources();
        houseResources.setHouseOwnerId(fangDongId);
        Wrapper<HouseResources> wrapper = new QueryWrapper<>(houseResources);
        return houseResourcesMapper.selectList(wrapper);
    }

}
