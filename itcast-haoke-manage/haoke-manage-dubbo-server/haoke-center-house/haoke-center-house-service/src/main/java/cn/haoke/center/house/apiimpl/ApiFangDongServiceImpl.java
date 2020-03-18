package cn.haoke.center.house.apiimpl;


import cn.haoke.center.house.api.ApiFangDongService;
import cn.haoke.center.house.dto.FangDongReqDto;
import cn.haoke.center.house.pojo.FangDong;
import cn.haoke.center.house.service.FangDongService;
import cn.haoke.center.house.service.HouseResourcesService;
import cn.haoke.common.constants.CommonConstant;
import cn.haoke.center.house.pojo.HouseResources;
import cn.haoke.center.house.vo.FangDongDetailVo;
import cn.haoke.common.vo.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(version = "1.0.0")
public class ApiFangDongServiceImpl implements ApiFangDongService {

    @Autowired
    private FangDongService fangDongService;
    @Autowired
    private HouseResourcesService houseResourcesService;


    @Override
    public PageInfo<FangDong> queryPage(FangDongReqDto dto) {
        Integer pageNum = dto.getPageNum();
        Integer pageSize = dto.getPageSize();
        if (pageNum == null) {
            pageNum = CommonConstant.DEFAULT_PAGE;
        }
        if (pageSize == null) {
            pageSize = CommonConstant.DEFAULT_PAGE_SIZE;
        }
        FangDong fangDong = new FangDong();
        BeanUtils.copyProperties(dto, fangDong);
        return fangDongService.queryFangDongList(dto.getPageNum(), pageSize, fangDong);
    }

    @Override
    public FangDong queryFangDongById(Long id) {
        return fangDongService.queryFangDongById(id);
    }

    @Override
    public boolean updateFangDong(FangDong fangDong) {
        return fangDongService.updateFangDong(fangDong);
    }

    @Override
    public FangDongDetailVo queryFangDongDetail(Long id) {
        FangDong fangDong = fangDongService.queryFangDongById(id);
        if(fangDong==null){
            return new FangDongDetailVo();
        }
        FangDongDetailVo fangDongDetailVo = new FangDongDetailVo();
        BeanUtils.copyProperties(fangDong,fangDongDetailVo);
        //查询房东的房源信息
        List<HouseResources> houseResources = houseResourcesService.queryByFangDongId(fangDong.getId());
        fangDongDetailVo.setHouseResourcesList(houseResources);
        return fangDongDetailVo;
    }
}
