package cn.haoke.mgmt.controller;

import cn.haoke.center.house.dto.FavoriteDto;
import cn.haoke.center.house.pojo.FavoriteEo;
import cn.haoke.common.exception.BusinessException;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.controller.base.AbstractBaseController;
import cn.haoke.mgmt.service.FavoriteService;
import cn.haoke.mgmt.util.SystemUserLoginInfoVo;
import cn.haoke.mgmt.util.SystemUserLoginUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("favorite")
public class FavoriteController extends AbstractBaseController {

    @Autowired
    private FavoriteService favoriteService;


    @PostMapping("queryByPage")
    public RestResponse<PageInfo> queryByPage(@RequestBody FavoriteDto dto){
        return favoriteService.queryByPage(dto);
    }

    @PostMapping
    public RestResponse<Long> addFavorite(@RequestBody FavoriteEo favoriteEo){
        SystemUserLoginInfoVo loginInfo = SystemUserLoginUtil.getLoginInfo();
        if(loginInfo==null||loginInfo.getUserId()==null){
            throw new BusinessException("登录信息失效！");
        }
        favoriteEo.setUserId(loginInfo.getUserId());
        return favoriteService.addFavorite(favoriteEo);
    }
    @DeleteMapping
    public RestResponse<Void> deleteFavorite(FavoriteEo favoriteEo){
        SystemUserLoginInfoVo loginInfo = SystemUserLoginUtil.getLoginInfo();
        if(loginInfo==null||loginInfo.getUserId()==null){
            throw new BusinessException("登录信息失效！");
        }
        favoriteEo.setUserId(loginInfo.getUserId());
        return favoriteService.deleteFavorite(favoriteEo);
    }
}
