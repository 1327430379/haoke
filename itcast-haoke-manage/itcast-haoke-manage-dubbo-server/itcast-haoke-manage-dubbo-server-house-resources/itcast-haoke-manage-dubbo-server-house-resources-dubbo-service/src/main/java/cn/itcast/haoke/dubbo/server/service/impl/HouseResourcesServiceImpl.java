package cn.itcast.haoke.dubbo.server.service.impl;

import cn.itcast.haoke.dubbo.server.pojo.HouseResources;
import cn.itcast.haoke.dubbo.server.service.BaseServiceImpl;
import cn.itcast.haoke.dubbo.server.service.HouseResourcesService;
import cn.itcast.haoke.dubbo.server.vo.PageInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class HouseResourcesServiceImpl extends BaseServiceImpl<HouseResources > implements HouseResourcesService {

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

        return super.save(houseResources);
    }

    @Override
    public PageInfo<HouseResources> queryHouseResourcesList(int page, int pageSize, HouseResources queryCondition) {

        QueryWrapper queryWrapper = new QueryWrapper();

        // 根据数据的更新时间做倒序排序
        queryWrapper.orderByDesc("updated");

        IPage iPage = super.queryPageList(queryWrapper, page, pageSize);

        return new PageInfo<HouseResources>(Long.valueOf(iPage.getTotal()).intValue(), page, pageSize, iPage.getRecords());
    }

    @Override
    public HouseResources queryHouseResourceById(Long id) {
       return super.queryById(id);

    }

    @Override
    public boolean updateHouseResource(HouseResources houseResources) {
        return super.update(houseResources)==1;
    }

}
