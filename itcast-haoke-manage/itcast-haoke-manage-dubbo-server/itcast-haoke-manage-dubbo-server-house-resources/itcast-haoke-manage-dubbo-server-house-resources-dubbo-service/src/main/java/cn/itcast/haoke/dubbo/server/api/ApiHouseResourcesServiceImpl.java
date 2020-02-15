package cn.itcast.haoke.dubbo.server.api;

import cn.itcast.haoke.dubbo.server.pojo.HouseResources;
import cn.itcast.haoke.dubbo.server.service.HouseResourcesService;
import cn.itcast.haoke.dubbo.server.vo.PageInfo;
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
