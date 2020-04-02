package cn.haoke.center.house.api;

import cn.haoke.center.house.pojo.FavoriteEo;
import cn.haoke.common.vo.RestResponse;

/***
 * 收藏api
 */
public interface ApiFavoriteService {

    RestResponse<Void> saveFavorite(FavoriteEo favoriteEo);
}
