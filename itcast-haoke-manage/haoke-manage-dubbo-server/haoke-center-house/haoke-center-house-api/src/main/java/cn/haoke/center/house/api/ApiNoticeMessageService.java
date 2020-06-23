package cn.haoke.center.house.api;


import cn.haoke.center.house.pojo.NoticeMessageEo;
import cn.haoke.common.vo.RestResponse;

import java.util.List;

public interface ApiNoticeMessageService {


    /***
     * 添加看房添加消息
     * @param eo
     * @return
     */
    RestResponse<Long> addNoticeMessage(NoticeMessageEo eo);

    /***
     * 修改消息
     * @param eo
     * @return
     */
    RestResponse<Void> saveNoticeMessage(NoticeMessageEo eo);

    /**
     * 查询用户的所有通知消息
     * @param userId
     * @return
     */
    RestResponse<List<NoticeMessageEo>> queryByUserId(Long userId);

    /**
     * id查询
     * @param id
     * @return
     */
    RestResponse<NoticeMessageEo> queryById(Long id);

    /**
     * 更新看房状态
     * @param id
     * @param seeHouseStatus
     * @return
     */
    RestResponse<Void> updateSeeHouseStatus(Long id, Integer seeHouseStatus);
}
