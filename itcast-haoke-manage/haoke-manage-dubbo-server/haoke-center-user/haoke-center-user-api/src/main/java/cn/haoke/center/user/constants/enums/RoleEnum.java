package cn.haoke.center.user.constants.enums;

public enum  RoleEnum {

    USER("user","租户"),
    HOUSE_OWNER("houseOwner","房东"),
    ADMIN("admin","管理员");


    private String role;
    private String desc;

    private RoleEnum(String role,String desc){
        this.role = role;
        this.desc = desc;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
