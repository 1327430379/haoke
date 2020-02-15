package cn.itcast.haoke.dubbo.server.service;

import cn.itcast.haoke.dubbo.server.pojo.Ad;
import cn.itcast.haoke.dubbo.server.vo.PageInfo;

public interface AdService {

    PageInfo<Ad> queryAdList(Ad ad,Integer page,Integer pageSize);
}
