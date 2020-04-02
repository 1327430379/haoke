package cn.haoke.center.house.api;

import cn.haoke.center.house.pojo.Ad;
import cn.haoke.common.vo.PageInfo;

/***
 * 广告服务
 */
public interface ApiAdService {

    PageInfo<Ad> queryAdList(Integer type, Integer page, Integer pageSize);

}