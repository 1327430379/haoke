package cn.haoke.mgmt.service;

import cn.haoke.center.house.api.ApiFavoriteService;
import cn.haoke.center.house.dto.FavoriteDto;
import cn.haoke.center.house.pojo.FavoriteEo;
import cn.haoke.common.exception.BusinessException;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.util.SystemUserLoginInfoVo;
import cn.haoke.mgmt.util.SystemUserLoginUtil;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class FavoriteService {

    @Reference(version = "1.0.0")
    private ApiFavoriteService apiFavoriteService;

    public RestResponse queryByPage(FavoriteDto dto) {
        FavoriteEo favoriteEo = new FavoriteEo();
        BeanUtils.copyProperties(dto, favoriteEo);
        SystemUserLoginInfoVo loginInfo = SystemUserLoginUtil.getLoginInfo();
        if(loginInfo==null||loginInfo.getUserId()==null){
            throw new BusinessException("登录信息失效！");
        }
        favoriteEo.setUserId(loginInfo.getUserId());
        return apiFavoriteService.queryByPage(favoriteEo, dto.getPageNum(), dto.getPageSize());
    }
    public RestResponse<Long> addFavorite(FavoriteEo favoriteEo){
       return apiFavoriteService.saveFavorite(favoriteEo);
    }

    public RestResponse<Void> deleteFavorite(FavoriteEo favoriteEo) {
        return apiFavoriteService.deleteUserFavorite(favoriteEo);
    }
}
