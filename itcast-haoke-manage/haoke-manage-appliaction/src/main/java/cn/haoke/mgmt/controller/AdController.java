package cn.haoke.mgmt.controller;

import cn.haoke.center.house.pojo.Ad;
import cn.haoke.mgmt.controller.base.AbstractBaseController;
import cn.haoke.mgmt.service.AdService;
import cn.haoke.mgmt.vo.WebResult;
import cn.haoke.common.vo.PageInfo;
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
public class AdController extends AbstractBaseController {
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
