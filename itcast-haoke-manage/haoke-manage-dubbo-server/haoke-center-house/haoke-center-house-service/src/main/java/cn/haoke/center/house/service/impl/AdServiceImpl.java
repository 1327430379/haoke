package cn.haoke.center.house.service.impl;

import cn.haoke.center.house.pojo.Ad;
import cn.haoke.center.house.service.AdService;
import cn.haoke.common.service.BaseServiceImpl;
import cn.haoke.common.vo.PageInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;

@Service
public class AdServiceImpl extends BaseServiceImpl<Ad> implements AdService {
    @Override
    public PageInfo<Ad> queryAdList(Ad ad, Integer page, Integer pageSize) {

        QueryWrapper<Ad> queryWrapper = new QueryWrapper<>(ad);
        //排序
        queryWrapper.orderByDesc("update_time");
        //查询的条件
        IPage iPage = super.queryPageList(null, page, pageSize);
        return new PageInfo(Long.valueOf(iPage.getTotal()).intValue(),page,pageSize
        ,iPage.getRecords());
    }

}
