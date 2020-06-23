package cn.haoke.center.user.api;

import cn.haoke.center.user.pojo.MessageEo;
import cn.haoke.common.vo.PageInfo;
import cn.haoke.common.vo.RestResponse;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface ApiMessageService {


    /**
     * 分页查询消息
     * @param eo
     * @param pageNum
     * @param pageSize
     * @return
     */
    RestResponse<PageInfo<MessageEo>> listMessageByPage(MessageEo eo,int pageNum,int pageSize);

    /***
     * 更改消息已读状态
     * @param id
     * @param readStatus
     * @return
     */
    RestResponse<Void> updateReadStatusById(Long id,Integer readStatus);

    /***
     * 通过用户id批量更新已读状态
     * @param userId
     * @param readStatus
     * @return
     */
    RestResponse<Void> updateReadStatusByUserId(Long userId,Integer readStatus);

    /***
     * 通过用户id删除消息
     * @param userId
     * @return
     */
    RestResponse<Void> deleteMessageByUserId(Long userId);

    /***
     * 异步添加消息
     * @param messageEo
     * @return
     */
    @Async
    RestResponse<Void> asyncInsertMessage(MessageEo messageEo);

    /***
     * 异步批量添加消息
     * @param messageEoList
     * @return
     */
    @Async
    RestResponse<Void> asyncInsertBatchMessageList(List<MessageEo> messageEoList);

}
