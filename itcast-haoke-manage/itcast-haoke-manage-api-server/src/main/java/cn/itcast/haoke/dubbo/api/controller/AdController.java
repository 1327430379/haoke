package cn.itcast.haoke.dubbo.api.controller;

import cn.itcast.haoke.dubbo.api.service.AdService;
import cn.itcast.haoke.dubbo.api.vo.WebResult;
import cn.itcast.haoke.dubbo.server.pojo.Ad;
import cn.itcast.haoke.dubbo.server.vo.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ad")
@CrossOrigin
public class AdController {
    @Autowired
    private AdService adService;

    /**
     * 查询首页广告
     */
    @GetMapping
    public WebResult queryIndexAd(){
        PageInfo<Ad> adPageInfo = adService.queryAdList(1, 1, 3);
        List<Ad> list = adPageInfo.getRecords();
        List<Object> result = new ArrayList<>();
        for (Ad ad : list) {
            Map<String,Object> map = new HashMap<>();
            map.put("original",ad.getUrl());
            result.add(map);
        }
        return WebResult.ok(result);
    }
}
