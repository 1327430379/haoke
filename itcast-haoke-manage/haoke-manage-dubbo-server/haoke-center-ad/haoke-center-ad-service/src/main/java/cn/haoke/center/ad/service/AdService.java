package cn.haoke.center.ad.service;

import cn.haoke.center.ad.pojo.Ad;
import cn.haoke.common.vo.PageInfo;

public interface AdService {

    PageInfo<Ad> queryAdList(Ad ad, Integer page, Integer pageSize);
}
