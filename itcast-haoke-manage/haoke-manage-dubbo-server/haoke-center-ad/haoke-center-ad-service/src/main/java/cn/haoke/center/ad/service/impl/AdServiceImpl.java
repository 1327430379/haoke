package cn.haoke.center.ad.service.impl;

import cn.haoke.center.ad.pojo.Ad;
import cn.haoke.center.ad.service.AdService;
import cn.haoke.common.service.BaseServiceImpl;
import cn.haoke.common.vo.PageInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;

@Service
public class AdServiceImpl extends BaseServiceImpl<Ad> implements AdService {
    @Override
    public PageInfo<Ad> queryAdList(Ad ad, Integer page, Integer pageSize) {

        QueryWrapper queryWrapper = new QueryWrapper();

        //排序
        queryWrapper.orderByDesc("updated");

        //查询的条件
        queryWrapper.eq("type",ad.getType());
        IPage iPage = super.queryPageList(queryWrapper, page, pageSize);
        return new PageInfo(Long.valueOf(iPage.getTotal()).intValue(),page,pageSize
        ,iPage.getRecords());
    }

}
