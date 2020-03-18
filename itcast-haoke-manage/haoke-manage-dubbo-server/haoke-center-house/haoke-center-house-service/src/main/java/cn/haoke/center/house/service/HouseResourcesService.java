package cn.haoke.center.house.service;

import cn.haoke.center.house.pojo.HouseResources;
import cn.haoke.common.vo.PageInfo;

import java.util.List;

public interface HouseResourcesService {

    /**
     * @param houseResources
     *
     * @return -1:输入的参数不符合要求，0：数据插入数据库失败，1：成功
     */
    int saveHouseResources(HouseResources houseResources);

    PageInfo<HouseResources> queryHouseResourcesList(int page, int pageSize, HouseResources queryCondition);

    HouseResources queryHouseResourceById(Long id);

    /***
     * 更新房源数据
     * @param houseResources
     * @return
     */
    boolean updateHouseResource(HouseResources houseResources);


    /***
     * 通过房东id查询房源列表
     * @param fangDongId
     * @return
     */
    List<HouseResources> queryByFangDongId(Long fangDongId);
}