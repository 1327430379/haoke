package cn.haoke.center.house.api;

import cn.haoke.center.house.dto.SeeHouseReqDto;
import cn.haoke.center.house.pojo.SeeHouseRequestEo;
import cn.haoke.center.house.vo.SeeHouseRecordVo;
import cn.haoke.common.vo.RestResponse;
import com.github.pagehelper.PageInfo;

import java.util.List;

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

    /***
     * 查询看房请求详情
     * @param id
     * @return
     */
    RestResponse<SeeHouseRecordVo> queryDetailById(Long id);

    /**
     * 分页查询看房请求
     * @param pageNum 页码
     * @param pageSize 页大小
     * @param eo
     * @return
     */
    RestResponse<PageInfo> listByPage(Integer pageNum,Integer pageSize,SeeHouseRequestEo eo);


    /***
     * 后台分页查询
     * @param dto
     * @param pageNum
     * @param pageSize
     * @return
     */
    RestResponse<PageInfo<SeeHouseRequestEo>> queryByPage(SeeHouseReqDto dto, Integer pageNum, Integer pageSize);

    /**
     * 后台删除看房请求
     * @param id
     * @return
     */
    RestResponse<Void> deleteSeeHouseRequest(Long id);

}
