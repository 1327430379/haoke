package cn.haoke.mgmt.service;


import cn.haoke.center.house.api.ApiFangDongService;
import cn.haoke.center.house.dto.FangDongReqDto;
import cn.haoke.center.house.pojo.FangDong;
import cn.haoke.center.house.vo.FangDongDetailVo;
import cn.haoke.common.vo.PageInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service
public class FangDongService {

    @Reference(version = "1.0.0")
    private ApiFangDongService apiFangDongService;

    public PageInfo<FangDong> queryPage(FangDongReqDto dto) {
        return apiFangDongService.queryPage(dto);
    }

    public FangDongDetailVo queryFangDongDetail(Long id) {
        return apiFangDongService.queryFangDongDetail(id);
    }
}
