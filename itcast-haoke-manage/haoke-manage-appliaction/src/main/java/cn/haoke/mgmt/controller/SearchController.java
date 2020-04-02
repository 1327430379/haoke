package cn.haoke.mgmt.controller;

import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.controller.base.AbstractBaseController;
import cn.haoke.mgmt.service.SearchService;
import cn.haoke.mgmt.vo.HouseData;
import cn.haoke.mgmt.vo.SearchResult;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.lucene.util.CollectionUtil;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

@RequestMapping("search")
@RestController
@CrossOrigin
public class SearchController extends AbstractBaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private SearchService searchService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @GetMapping
    public SearchResult search(@RequestParam("keyword") String keyword,
                               @RequestParam(value = "page", defaultValue = "1") Integer page) {
        if (page > 100) {//防止爬虫抓取过多的数据
            page = 1;
        }
        String redisKey = "HAOKE_SEARCH_HOT_WORD";

        SearchResult search = this.searchService.search(keyword, page);

        if (search.getTotalPage() <= 1) {
            Set set = this.redisTemplate.opsForZSet().reverseRange(redisKey, 0, 4);
            search.setHotWord(set);
        }


        int count = ((Math.max(search.getTotalPage(), 1) - 1) * SearchService.ROWS) + search.getList().size();
        this.redisTemplate.opsForZSet().add(redisKey, keyword, count);

        LOGGER.info("[Search]关键字为：" + keyword + ",数量为：" + count);
        return search;
    }

    @GetMapping("/update")
    public RestResponse update() throws IOException {
        UpdateQuery updateQuery = new UpdateQueryBuilder().withId("").build();

        UpdateRequest updateRequest = new UpdateRequest();
        updateQuery.setIndexName("haoke");
        updateQuery.setType("house");
        updateQuery.setUpdateRequest(updateRequest);
        updateRequest.doc(XContentFactory.jsonBuilder().startObject()
                .field("image", "SH2109196385697144832.jpg,SH2112189710775894016.jpg")
                .endObject());

        UpdateResponse update = elasticsearchTemplate.update(updateQuery);
        return new RestResponse(update);
    }

    @PostMapping("/updateBatch")
    public RestResponse updateBatch() {
        PageRequest pageRequest = PageRequest.of(1, 3000);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withPageable(pageRequest).build();

        AggregatedPage<HouseData> housePage = this.elasticsearchTemplate.queryForPage(searchQuery,
                HouseData.class, new SearchResultMapper() {
                    @Override
                    public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> clazz, Pageable pageable) {
                        if (searchResponse.getHits().totalHits == 0) {
                            //没有命中数据
                            return new AggregatedPageImpl<>(Collections.emptyList(), pageable, 0L);
                        }
                        List<T> list = new ArrayList<>();
                        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
                            T obj = (T) ReflectUtils.newInstance(clazz);
                            try {
                                //写入id
                                FieldUtils.writeField(obj, "id", searchHit.getId(), true);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }

                            //非高亮字段的数据写入
                            for (Map.Entry<String, Object> entry : searchHit.getSourceAsMap().entrySet()) {
                                //forceAccess:强制读取私有属性
                                Field field = FieldUtils.getField(clazz, entry.getKey(),true);
                                if(null == field){
                                    continue;
                                }

                                try {
                                    FieldUtils.writeField(obj, entry.getKey(), entry.getValue(), true);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }
                            list.add(obj);

                        }
                        return new AggregatedPageImpl<>(list,pageable,searchResponse.getHits().totalHits);
                    }
                });
        List<HouseData> content = housePage.getContent();
        System.out.println(content);
//        if (!CollectionUtils.isEmpty(houseData)) {
//            for (HouseData vo : houseData) {
//
//
//                UpdateQuery updateQuery = new UpdateQueryBuilder().withId("").build();
//
//                UpdateRequest updateRequest = new UpdateRequest();
//                updateQuery.setIndexName("haoke");
//                updateQuery.setType("house");
//                updateQuery.setId(vo.getId());
//                updateQuery.setUpdateRequest(updateRequest);
//                try {
//                    updateRequest.doc(XContentFactory.jsonBuilder().startObject()
//                            .field("image", vo.getImage() + ",SH2112189710775894016.jpg")
//                            .endObject());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                UpdateResponse update = elasticsearchTemplate.update(updateQuery);
//
//
//            }
//        }
        return new RestResponse();
    }


}
