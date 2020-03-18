package cn.haoke.center.house.service;

import cn.haoke.center.house.pojo.FangDong;
import cn.haoke.common.vo.PageInfo;

public interface FangDongService {

    /**
     * @param fangDong 请求参数
     * @return -1:输入的参数不符合要求，0：数据插入数据库失败，1：成功
     */
    int saveFangDong(FangDong fangDong);

    /***
     * 分页查询
     * @param page 页码
     * @param pageSize 页大小
     * @param queryCondition 查询条件
     * @return
     */
    PageInfo<FangDong> queryFangDongList(int page, int pageSize, FangDong queryCondition);

    /***
     * 通过id查询
     * @param id
     * @return
     */
    FangDong queryFangDongById(Long id);

    /***
     * 更新房源数据
     * @param fangDong
     * @return
     */
    boolean updateFangDong(FangDong fangDong);
}
