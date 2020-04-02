package cn.haoke.mgmt.controller;

import cn.haoke.center.house.dto.FavoriteDto;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.controller.base.AbstractBaseController;
import cn.haoke.mgmt.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("favorite")
public class FavoriteController extends AbstractBaseController {

    @Autowired
    private FavoriteService favoriteService;


    @PostMapping("queryByPage")
    public RestResponse queryByPage(@RequestBody FavoriteDto dto){
        return favoriteService.queryByPage(dto);
    }
}
