package cn.haoke.mgmt.graphql;

import cn.haoke.mgmt.service.HouseResourcesService;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HouseResourcesDataFether implements MyDataFetcher {

    @Autowired
    private HouseResourcesService houseResourcesService;

    @Override
    public String filedName() {
        return "HouseResources";
    }

    @Override
    public Object dataFetcher(DataFetchingEnvironment environment) {
       Long id=  environment.getArgument("id");
        return this.houseResourcesService.queryHouseResourcesById(id);
    }
}
