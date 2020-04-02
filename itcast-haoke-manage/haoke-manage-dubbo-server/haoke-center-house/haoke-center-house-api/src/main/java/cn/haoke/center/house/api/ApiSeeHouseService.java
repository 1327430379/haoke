package cn.haoke.center.house.api;

import cn.haoke.center.house.pojo.SeeHouseRequestEo;
import cn.haoke.common.vo.RestResponse;

public interface ApiSeeHouseService {


    /***
     * 保存看房请求
     * @param requestEo
     * @return
     */
    RestResponse<Long> saveRequest(SeeHouseRequestEo requestEo);

    /***
     * 更新请求状态
     * @param id
     * @param requestStatus
     * @return
     */
    RestResponse<Void> updateRequestStatus(Long id,Integer requestStatus);

    /***
     * 通过id查询
     * @param id
     * @return
     */
    RestResponse<SeeHouseRequestEo> queryById(Long id);
}
