package cn.itcast.haoke.dubbo.api.service;

import cn.itcast.haoke.dubbo.api.vo.HouseData;
import cn.itcast.haoke.dubbo.api.vo.SearchResult;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public static final Integer ROWS = 10;

    public SearchResult search(String keyword,Integer page){
        PageRequest pageRequest = PageRequest.of(page - 1, ROWS);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("title",
                        keyword).operator(Operator.AND))//Match查询
                .withPageable(pageRequest)
                .withHighlightFields(new HighlightBuilder.Field("title"))//设置高亮
                .build();

        AggregatedPage<HouseData> housePage = this.elasticsearchTemplate.queryForPage(searchQuery, HouseData.class);

        return new SearchResult(housePage.getTotalPages(),housePage.getContent());
    }
}
