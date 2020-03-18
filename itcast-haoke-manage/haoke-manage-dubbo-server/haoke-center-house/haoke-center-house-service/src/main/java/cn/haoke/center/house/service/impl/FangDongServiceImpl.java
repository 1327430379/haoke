package cn.haoke.center.house.service.impl;

import cn.haoke.center.house.pojo.FangDong;
import cn.haoke.center.house.service.FangDongService;
import cn.haoke.common.service.BaseServiceImpl;
import cn.haoke.common.vo.PageInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class FangDongServiceImpl extends BaseServiceImpl<FangDong> implements FangDongService {


    @Override
    public int saveFangDong(FangDong fangDong) {

        // 添加校验或者是其他的一些逻辑 todo

        return super.save(fangDong);
    }

    @Override
    public PageInfo<FangDong> queryFangDongList(int page, int pageSize, FangDong queryCondition) {
        QueryWrapper queryWrapper = new QueryWrapper();

        // 根据数据的更新时间做倒序排序
        queryWrapper.orderByDesc("update_time");

        IPage iPage = super.queryPageList(queryWrapper, page, pageSize);

        return new PageInfo<FangDong>(Long.valueOf(iPage.getTotal()).intValue(), page, pageSize, iPage.getRecords());
    }

    @Override
    public FangDong queryFangDongById(Long id) {
        return super.queryById(id);
    }

    @Override
    public boolean updateFangDong(FangDong fangDong) {
        return super.update(fangDong) == 1;
    }
}
