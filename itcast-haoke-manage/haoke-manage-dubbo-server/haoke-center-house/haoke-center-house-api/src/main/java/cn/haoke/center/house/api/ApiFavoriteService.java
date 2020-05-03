package cn.haoke.center.house.api;

import cn.haoke.center.house.pojo.FavoriteEo;
import cn.haoke.center.house.vo.FavoriteVo;
import cn.haoke.common.vo.RestResponse;
import com.github.pagehelper.PageInfo;

import java.util.List;

/***
 * 收藏api
 */
public interface ApiFavoriteService {

    /***
     * 保存收藏
     * @param favoriteEo 保存实体
     * @return RestResponse
     */
    RestResponse<Long> saveFavorite(FavoriteEo favoriteEo);

    /***
     * 分页查询
     * @param favoriteEo 查询条件
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return PageInfo
     */
    RestResponse<PageInfo<FavoriteVo>> queryByPage(FavoriteEo favoriteEo, Integer pageNum, Integer pageSize);

    /***
     * 通过条件查询
     * @param favoriteEo
     * @return
     */
    RestResponse<List<FavoriteVo>> queryByCondition(FavoriteEo favoriteEo);

    /***
     * 删除用户收藏 的房源
     * @param favoriteEo 请求实体
     * @return Void
     */
    RestResponse<Void> deleteUserFavorite(FavoriteEo favoriteEo);
}
