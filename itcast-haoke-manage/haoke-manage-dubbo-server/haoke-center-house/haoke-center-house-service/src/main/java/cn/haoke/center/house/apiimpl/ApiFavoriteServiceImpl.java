package cn.haoke.center.house.apiimpl;

import cn.haoke.center.house.api.ApiFavoriteService;
import cn.haoke.center.house.constants.CustomeConstant;
import cn.haoke.center.house.dto.FavoriteDto;
import cn.haoke.center.house.mapper.FavoriteMapper;
import cn.haoke.center.house.pojo.FavoriteEo;
import cn.haoke.center.house.vo.FavoriteVo;
import cn.haoke.common.constants.CommonConstant;
import cn.haoke.common.exception.BusinessException;
import cn.haoke.common.vo.RestResponse;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.json.JSON;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service(version = "1.0.0")
public class ApiFavoriteServiceImpl implements ApiFavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;
    @Override
    public RestResponse<Long> saveFavorite(FavoriteEo favoriteEo) {

        if(favoriteEo==null || StringUtils.isEmpty(favoriteEo.getHouseId())){
            throw new BusinessException("请求数据为空！");
        }
        if(favoriteEo.getId()==null){
            //添加
            Date date = new Date();
            favoriteEo.setCreateTime(date);
            favoriteEo.setUpdateTime(date);
            favoriteMapper.insert(favoriteEo);
        }else{
            //更新
            favoriteMapper.updateById(favoriteEo);
        }

        return new RestResponse<>(favoriteEo.getId());
    }

    @Override
    public  RestResponse<PageInfo<FavoriteVo>> queryByPage(FavoriteEo favoriteEo, Integer pageNum, Integer pageSize) {
        if(pageNum!=null && pageSize!=null){
            PageHelper.startPage(pageNum,pageSize);
        }
        List<FavoriteVo> favoriteVos = favoriteMapper.queryList(favoriteEo);
        PageInfo<FavoriteVo> pageInfo = new PageInfo<>(favoriteVos);
        return new RestResponse<>(pageInfo);
    }

    @Override
    public RestResponse<List<FavoriteVo>> queryByCondition(FavoriteEo favoriteEo) {
        List<FavoriteVo> favoriteVos = favoriteMapper.queryList(favoriteEo);
        return new RestResponse<>(favoriteVos);
    }

    @Override
    public RestResponse<Void> deleteUserFavorite(FavoriteEo favoriteEo) {
        Wrapper<FavoriteEo> wrapper = new QueryWrapper<>(favoriteEo);
        favoriteMapper.delete(wrapper);
        return RestResponse.VOID;
    }
}
