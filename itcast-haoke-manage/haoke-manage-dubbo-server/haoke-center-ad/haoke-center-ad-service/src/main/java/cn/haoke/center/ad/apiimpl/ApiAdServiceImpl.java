package cn.haoke.center.ad.apiimpl;

import cn.haoke.center.ad.api.ApiAdService;
import cn.haoke.center.ad.pojo.Ad;
import cn.haoke.center.ad.service.AdService;
import cn.haoke.common.vo.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service(version = "1.0.0")
public class ApiAdServiceImpl implements ApiAdService {

    @Autowired
    private AdService adService;

    @Override
    public PageInfo<Ad> queryAdList(Integer type, Integer page, Integer pageSize) {
        Ad ad = new Ad();
        ad.setType(type);
        return this.adService.queryAdList(ad, page, pageSize);

    }
}
