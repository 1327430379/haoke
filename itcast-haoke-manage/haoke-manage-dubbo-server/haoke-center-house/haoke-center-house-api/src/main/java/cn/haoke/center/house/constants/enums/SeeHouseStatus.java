package cn.haoke.center.house.constants.enums;


public enum  SeeHouseStatus {

    WAIT_CONFIRM(0,"待确认"),
    WAIT_SEE_HOUSE(1,"待看房"),
    CANCELED(2,"已取消"),
    FINISHED_HAS_SEE(3,"已完成看房"),
    FINISHED_NOT_SEE(4,"已完成未看房"),
    REFUSE_SEE_HOUSE(5,"拒绝看房");
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
