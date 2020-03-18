package cn.haoke.center.house.constants.enums;


public enum  SeeHouseStatus {

    WAIT_CONFIRM(0,"待确认"),
    WAIT_SEE_HOUSE(1,"待看房"),
    CANCELED(2,"已取消"),
    FINISHED(3,"已完成");

    private Integer status;

    private String desc;

    private SeeHouseStatus(Integer status,String desc){
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
