package cn.haoke.center.user.constants.enums;

import cn.haoke.center.user.pojo.MessageEo;

public enum  MessageStatus {


    READ(1,"已读"),
    UN_READ(0,"未读");



    private Integer status;
    private String desc;
    private MessageStatus(Integer status,String desc){
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
