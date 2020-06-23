package cn.haoke.mgmt.graphql;

import cn.haoke.mgmt.service.MongoHouseService;
import cn.haoke.mgmt.vo.map.MapHouseXY;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapHouseDateFetcher implements MyDataFetcher {

    @Autowired
    private MongoHouseService mongoHouseService;

    @Override
    public String filedName() {
        return "MapHouseData";
    }

    @Override
    public Object dataFetcher(DataFetchingEnvironment environment) {
        List<MapHouseXY> list = new ArrayList<>();
        Float lng = ((Double)environment.getArgument("lng")).floatValue();
        Float lat = ((Double)environment.getArgument("lat")).floatValue();
        Integer zoom = environment.getArgument("zoom");
        System.out.println("lng=" + lng + ",lat=" + lat + ",zoom=" + zoom);

        return this.mongoHouseService.queryMapHouseData(lng,lat,zoom);
    }
}
