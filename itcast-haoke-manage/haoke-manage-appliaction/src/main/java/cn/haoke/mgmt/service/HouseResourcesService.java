package cn.haoke.mgmt.service;

import cn.haoke.center.house.api.ApiFavoriteService;
import cn.haoke.center.house.api.ApiHouseResourcesService;
import cn.haoke.center.house.dto.HouseResourceReqDto;
import cn.haoke.center.house.pojo.FavoriteEo;
import cn.haoke.center.house.vo.FavoriteVo;
import cn.haoke.center.user.api.ApiUserService;
import cn.haoke.center.user.pojo.UserEo;
import cn.haoke.common.constants.CommonConstant;
import cn.haoke.common.exception.BusinessException;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.util.SystemUserLoginInfoVo;
import cn.haoke.mgmt.util.SystemUserLoginUtil;
import cn.haoke.mgmt.vo.HouseResourcesVo;
import cn.haoke.mgmt.vo.Pagination;
import cn.haoke.mgmt.vo.TableResult;
import cn.haoke.center.house.pojo.HouseResources;

import com.github.pagehelper.PageInfo;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HouseResourcesService {

    @Reference(version = "1.0.0")
    private ApiHouseResourcesService apiHouseResourcesService;

    @Reference(version = "1.0.0")
    private ApiFavoriteService apiFavoriteService;

    @Reference(version = "1.0.0")
    private ApiUserService apiUserService;

    public boolean save(HouseResources houseResources) {
        int result =
                this.apiHouseResourcesService.saveHouseResources(houseResources);
        return result == 1;
    }


    public PageInfo<HouseResources> queryList(HouseResourceReqDto dto, Integer currentPage, Integer pageSize) {
       return apiHouseResourcesService.queryHouseResourcesList(currentPage, pageSize, dto);
    }

    /**
     * 根据Id查询房源数据
     * @param id
     * @return
     */
    public HouseResourcesVo queryHouseResourcesById(String id){
        SystemUserLoginInfoVo loginInfo = SystemUserLoginUtil.getLoginInfo();
        if(loginInfo==null){
            loginInfo = new SystemUserLoginInfoVo();
            loginInfo.setUserId(CommonConstant.DEFAULT_USER_ID);
          //  throw new BusinessException("登录信息失效");
        }
        //调用dubbo中的服务查询数据
        HouseResources houseResources = this.apiHouseResourcesService.queryHouseResourceById(id);
        HouseResourcesVo vo = null;
        if(houseResources!=null){
            //判断是否收藏
            vo = new HouseResourcesVo();
            BeanUtils.copyProperties(houseResources,vo);
            FavoriteEo favoriteEo = new FavoriteEo();
            favoriteEo.setHouseId(id);
            favoriteEo.setUserId(loginInfo.getUserId());
            List<FavoriteVo> favoriteVos = apiFavoriteService.queryByCondition(favoriteEo).getData();
            if(CollectionUtils.isNotEmpty(favoriteVos)){
                //已经收藏过
                vo.setIsFavorite(true);
            }else{
                vo.setIsFavorite(false);
            }
            //查出房东信息
            UserEo userEo = apiUserService.queryById(houseResources.getHouseOwnerId()).getData();
            vo.setHouseOwnerName(userEo.getName());
            vo.setHouseOwnerAvatar(userEo.getAvatar());

        }
        return vo;
    }

    public RestResponse<Void> update(HouseResources houseResources) {
         apiHouseResourcesService.updateHouseResource(houseResources);
         return RestResponse.VOID;
    }

    public Map queryByRentStatus(HouseResources houseResources) {

        return apiHouseResourcesService.queryByRentStatus(houseResources);
    }

    public RestResponse<Void> deleteById(String id) {

      apiHouseResourcesService.deleteById(id);
      return new RestResponse<>();
    }
}