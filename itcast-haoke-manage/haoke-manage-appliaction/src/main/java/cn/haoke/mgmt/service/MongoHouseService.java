package cn.haoke.mgmt.service;


import cn.haoke.mgmt.pojo.MongoHouse;
import cn.haoke.mgmt.vo.map.MapHouseDataResult;
import cn.haoke.mgmt.vo.map.MapHouseXY;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MongoHouseService {

    public static Map<Integer, Double> BAIDU_ZOOM = new HashMap<>();

    static {
        BAIDU_ZOOM.put(19, 20d / 1000); //单位为km
        BAIDU_ZOOM.put(18, 50d / 1000);
        BAIDU_ZOOM.put(17, 100d / 1000);
        BAIDU_ZOOM.put(16, 200d / 1000);
        BAIDU_ZOOM.put(15, 500d / 1000);
        BAIDU_ZOOM.put(14, 1d);
        BAIDU_ZOOM.put(13, 2d);
        BAIDU_ZOOM.put(12, 5d);
        BAIDU_ZOOM.put(11, 10d);
        BAIDU_ZOOM.put(10, 20d);
        BAIDU_ZOOM.put(9, 25d);
        BAIDU_ZOOM.put(8, 50d);
        BAIDU_ZOOM.put(7, 100d);
        BAIDU_ZOOM.put(6, 200d);
        BAIDU_ZOOM.put(5, 500d);
        BAIDU_ZOOM.put(4, 1000d);
        BAIDU_ZOOM.put(3, 2000d);
        BAIDU_ZOOM.put(2, 5000d);
        BAIDU_ZOOM.put(1, 10000d);
    }

    @Autowired
    private MongoTemplate mongoTemplate;

    public MapHouseDataResult queryMapHouseData(Float lng, Float lat, Integer zoom) {
        double distance = BAIDU_ZOOM.get(zoom) * 1.5 / 111.12;//15倍距离范围，根据实际需求调整；

        Query query = Query.query(Criteria.where("loc").near(new Point(lng, lat)).maxDistance(distance));

        List<MongoHouse> mongoHouses = this.mongoTemplate.find(query, MongoHouse.class);

        List<MapHouseXY> list = new ArrayList<>();

        for (MongoHouse mongoHouse : mongoHouses) {

            list.add(new MapHouseXY(mongoHouse.getLoc()[0],mongoHouse.getLoc()[1]));
        }

        return new MapHouseDataResult(list);
    }
}
