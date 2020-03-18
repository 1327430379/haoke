package cn.haoke.mgmt.service;

import cn.haoke.center.house.api.ApiHouseResourcesService;
import cn.haoke.mgmt.vo.Pagination;
import cn.haoke.mgmt.vo.TableResult;
import cn.haoke.center.house.pojo.HouseResources;
import cn.haoke.common.vo.PageInfo;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service
public class HouseResourcesService {

    @Reference(version = "1.0.0")
    private ApiHouseResourcesService apiHouseResourcesService;

    public boolean save(HouseResources houseResources) {
        int result =
                this.apiHouseResourcesService.saveHouseResources(houseResources);
        return result == 1;
    }


    public TableResult<HouseResources> queryList(HouseResources houseResources, Integer currentPage, Integer pageSize) {
        PageInfo<HouseResources> pageInfo = this.apiHouseResourcesService.
                queryHouseResourcesList(currentPage, pageSize, houseResources);
        return new TableResult<>(pageInfo.getRecords(), new Pagination(currentPage, pageSize, pageInfo.getTotal()));
    }

    /**
     * 根据Id查询房源数据
     * @param id
     * @return
     */
    public HouseResources queryHouseResourcesById(Long id){
       //调用dubbo中的服务查询数据
        return this.apiHouseResourcesService.queryHouseResourceById(id);
    }

    public boolean update(HouseResources houseResources) {
        return apiHouseResourcesService.updateHouseResource(houseResources);
    }
}