package cn.haoke.center.house.constants.enums;

public enum  OrderStatus {

    WAIT_CONFIRM(0,"待房东确认"),
    NON_PAYMENT(1,"未付款"),
    PAID(2,"已付款");

    private Integer status;
    private String desc;

    private OrderStatus(Integer status,String desc){
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
