package cn.haoke.mgmt.service;

import cn.haoke.center.ad.api.ApiAdService;
import cn.haoke.center.ad.pojo.Ad;
import cn.haoke.common.vo.PageInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service
public class AdService {

    @Reference(version = "1.0.0")
    private ApiAdService apiAdService;

    public PageInfo<Ad> queryAdList(Integer type,Integer page,Integer pageSize){
        return apiAdService.queryAdList(type,page,pageSize);

    }
}
