package cn.itcast.haoke.dubbo.api.graphql;

import cn.itcast.haoke.dubbo.api.service.AdService;
import cn.itcast.haoke.dubbo.api.vo.ad.index.IndexAdResult;
import cn.itcast.haoke.dubbo.api.vo.ad.index.IndexAdResultData;
import cn.itcast.haoke.dubbo.server.pojo.Ad;
import cn.itcast.haoke.dubbo.server.vo.PageInfo;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdDataFetcher implements MyDataFetcher {
    @Autowired
    private AdService adService;

    @Override
    public String filedName() {
        return "IndexAdList";
    }

    @Override
    public Object dataFetcher(DataFetchingEnvironment environment) {
        PageInfo<Ad> pageInfo = this.adService.queryAdList(1, 1, 3);
        if(pageInfo!=null){
            List<Ad> ads = pageInfo.getRecords();
            List<IndexAdResultData> list = new ArrayList<>();
            for (Ad ad : ads) {
                list.add(new IndexAdResultData(ad.getUrl()));
            }
            return new IndexAdResult(list);
        }
        return null;
    }
}
