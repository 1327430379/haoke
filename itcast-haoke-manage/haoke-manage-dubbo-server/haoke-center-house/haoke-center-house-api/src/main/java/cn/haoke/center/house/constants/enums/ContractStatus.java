package cn.haoke.center.house.constants.enums;

public enum ContractStatus {

    WAIT_SIGN_UP(0,"待签约"),
    SIGN_UP(1,"签约中"),
    OVER(2,"已结束"),
    OVERDUE(3,"已逾期");

    private Integer status;
    private String desc;

    private ContractStatus(Integer status,String desc){
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
