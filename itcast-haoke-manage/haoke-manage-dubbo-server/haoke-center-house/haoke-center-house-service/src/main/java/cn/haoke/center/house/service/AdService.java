package cn.haoke.center.house.service;

import cn.haoke.center.house.pojo.Ad;
import cn.haoke.common.vo.PageInfo;

public interface AdService {

    PageInfo<Ad> queryAdList(Ad ad, Integer page, Integer pageSize);
}
