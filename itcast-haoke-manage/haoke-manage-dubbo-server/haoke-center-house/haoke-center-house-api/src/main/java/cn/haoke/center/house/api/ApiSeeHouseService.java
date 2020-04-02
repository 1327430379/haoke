package cn.haoke.center.house.api;

import cn.haoke.center.house.pojo.SeeHouseRequestEo;
import cn.haoke.common.vo.RestResponse;

public interface ApiSeeHouseService {


    /***
     * 添加看房请求
     * @param requestEo 请求实体
     * @return  RestResponse<Long>
     */
    RestResponse<Long> addRequest(SeeHouseRequestEo requestEo);

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
