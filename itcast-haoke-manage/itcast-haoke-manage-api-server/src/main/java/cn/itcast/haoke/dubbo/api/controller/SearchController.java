package cn.itcast.haoke.dubbo.api.controller;

import cn.itcast.haoke.dubbo.api.service.SearchService;
import cn.itcast.haoke.dubbo.api.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("search")
@RestController
@CrossOrigin
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping
    public SearchResult search(@RequestParam("keyword")String keyword,
                               @RequestParam(value = "page",defaultValue = "1")Integer page){
        if(page >100){//防止爬虫抓取过多的数据
            page = 1;
        }

        return this.searchService.search(keyword,page);
    }
}
