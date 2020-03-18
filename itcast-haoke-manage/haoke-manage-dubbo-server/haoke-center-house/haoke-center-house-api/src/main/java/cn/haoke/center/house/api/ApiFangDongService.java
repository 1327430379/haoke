package cn.haoke.center.house.api;

import cn.haoke.center.house.dto.FangDongReqDto;
import cn.haoke.center.house.pojo.FangDong;
import cn.haoke.center.house.vo.FangDongDetailVo;
import cn.haoke.common.vo.PageInfo;

public interface ApiFangDongService {


    /***
     * 分页查询房东列表
     * @param dto
     * @return
     */
    PageInfo<FangDong> queryPage(FangDongReqDto dto);

    /**
     * 根据房东d查找
     * @param id
     * @return
     */
    FangDong queryFangDongById(Long id);

    /**
     * 更新房东数据
     * @param fangDong
     * @return
     */
    boolean updateFangDong(FangDong fangDong);

    /***
     * 查询房东详情信息
     * @param id
     * @return
     */
    FangDongDetailVo queryFangDongDetail(Long id);
}
