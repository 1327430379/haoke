package cn.haoke.center.house.api;

import cn.haoke.center.house.pojo.FavoriteEo;
import cn.haoke.center.house.vo.FavoriteVo;
import cn.haoke.common.vo.PageInfo;
import cn.haoke.common.vo.RestResponse;

/***
 * 收藏api
 */
public interface ApiFavoriteService {

    /***
     * 保存收藏
     * @param favoriteEo 保存实体
     * @return RestResponse
     */
    RestResponse<Void> saveFavorite(FavoriteEo favoriteEo);

    /***
     * 分页查询
     * @param favoriteEo 查询条件
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return PageInfo
     */
    RestResponse<PageInfo<FavoriteVo>> queryByPage(FavoriteEo favoriteEo,Integer pageNum,Integer pageSize);
}
