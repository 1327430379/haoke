package cn.itcast.haoke.dubbo.server.api;

import cn.itcast.haoke.dubbo.server.pojo.Ad;
import cn.itcast.haoke.dubbo.server.vo.PageInfo;

public interface ApiAdService {
    /**
     * 分页广告查询
     * @param type
     * @param page
     * @param pageSize
     * @return
     */
    PageInfo<Ad> queryAdList(Integer type,Integer page,Integer pageSize);
}
