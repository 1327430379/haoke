package cn.haoke.mgmt.service;


import cn.haoke.center.house.api.ApiFangDongService;
import cn.haoke.center.house.dto.FangDongReqDto;
import cn.haoke.center.house.pojo.FangDong;
import cn.haoke.center.house.vo.FangDongDetailVo;
import cn.haoke.center.user.api.ApiUserService;
import cn.haoke.center.user.dto.UserSearchDto;
import cn.haoke.center.user.pojo.UserEo;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.mgmt.util.TableResultUtils;
import cn.haoke.mgmt.vo.TableResult;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service
public class FangDongService {

    @Reference(version = "1.0.0")
    private ApiUserService apiUserService;

    public RestResponse<TableResult> queryPage(UserSearchDto dto) {
        PageInfo<UserEo> pageInfo = apiUserService.queryList(dto).getData();
        TableResult result = TableResultUtils.pageInfo2TableResult(pageInfo);
        return new RestResponse<>(result);
    }

    public RestResponse<UserEo> queryFangDongDetail(Long id) {
        return apiUserService.queryById(id);
    }
}
