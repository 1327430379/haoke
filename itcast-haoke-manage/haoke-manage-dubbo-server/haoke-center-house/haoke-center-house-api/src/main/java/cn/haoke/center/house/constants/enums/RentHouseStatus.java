package cn.haoke.center.house.constants.enums;

public enum RentHouseStatus {
    UN_RENT(0,"未出租"),
    RENT_OUT(1,"已租"),
    EXPIRED(2,"已到期");


    private Integer status;
    private String desc;
    private RentHouseStatus(Integer status,String desc){
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
