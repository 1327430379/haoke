package cn.haoke.mgmt.vo;

import java.io.Serializable;

public class NoticeMessageVo implements Serializable {

    /***
     * 目标用户类型（1：租客用户，2：房东用户）
     */
    private Integer targetType;

    /***
     * 标题
     */
    private String title;

    /***
     * 通知内容
     */
    private String content;

    /***
     * 关联的数据id
     */
    private Long dataId;

    /**
     * 目标用户Id
     */
    private Long userId;

    /***
     * 发送者id
     */
    private Long senderId;

    /***
     * 已读状态
     */
    private Integer readStatus;

    /***
     * 请求看房的状态
     */
    private Integer status;
}
