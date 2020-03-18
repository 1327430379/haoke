package cn.haoke.center.house.apiimpl;

import cn.haoke.center.house.api.ApiHouseResourcesService;
import cn.haoke.center.house.service.HouseResourcesService;
import cn.haoke.center.house.pojo.HouseResources;
import cn.haoke.common.vo.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service(version = "1.0.0")
public class ApiHouseResourcesServiceImpl implements ApiHouseResourcesService {

    @Autowired
    private HouseResourcesService houseResourcesService;

    @Override
    public int saveHouseResources(HouseResources houseResources) {
        return this.houseResourcesService.saveHouseResources(houseResources);
    }

    @Override
    public PageInfo<HouseResources> queryHouseResourcesList(int page, int pageSize, HouseResources queryCondition) {
        return this.houseResourcesService.queryHouseResourcesList(page, pageSize, queryCondition);
    }

    @Override
    public HouseResources queryHouseResourceById(Long id) {
        return this.houseResourcesService.queryHouseResourceById(id);

    }

    @Override
    public boolean updateHouseResource(HouseResources houseResources) {
        return this.houseResourcesService.updateHouseResource(houseResources);
    }

}
