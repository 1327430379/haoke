package cn.haoke.center.house.constants.enums;

/***
 * 请求看房状态
 */
public enum RequestSesHouseStatus {

    WAIT_CONFIRM(0,"待确认"),
    AGREE_SEE_HOUSE(1,"同意看房"),
    REFUSE_SEE_HOUSE(2,"拒绝看房");

    private Integer status;
    private String desc;
    private RequestSesHouseStatus(Integer status,String desc){
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
