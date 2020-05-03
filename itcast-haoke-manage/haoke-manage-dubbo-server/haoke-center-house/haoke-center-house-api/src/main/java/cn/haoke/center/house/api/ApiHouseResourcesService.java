package cn.haoke.center.house.api;

import cn.haoke.center.house.dto.HouseResourceReqDto;
import cn.haoke.center.house.pojo.HouseResources;
import cn.haoke.common.vo.RestResponse;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface ApiHouseResourcesService {

    /**
     * 新增房源
     *
     * @param houseResources
     * @return -1:输入的参数不符合要求，0：数据插入数据库失败，1：成功
     */
    int saveHouseResources(HouseResources houseResources);

    /**
     * 分页查询房源列表
     *
     * @param page 当前页
     * @param pageSize 页面大小
     * @param queryCondition 查询条件
     * @return
     */
    PageInfo<HouseResources> queryHouseResourcesList(Integer page, Integer pageSize,
                                                     HouseResourceReqDto queryCondition);

    /**
     * 根据房源Id查找
     * @param id
     * @return
     */
    HouseResources queryHouseResourceById(String id);

    /**
     * 更新房源数据
     * @param houseResources
     * @return
     */
    void updateHouseResource(HouseResources houseResources);

    /***
     * 找出已租和未租的房源集合
     * @param houseResources
     * @return
     */
    Map queryByRentStatus(HouseResources houseResources);

    /**
     * 删除房源
     * @param id
     */
    void deleteById(String id);
}