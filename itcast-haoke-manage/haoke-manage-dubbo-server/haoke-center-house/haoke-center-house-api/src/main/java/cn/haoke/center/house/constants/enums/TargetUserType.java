package cn.haoke.center.house.constants.enums;

public enum TargetUserType {

    USER(1, "租客"),
    HOUSE_OWNER(2, "房东");
    private int type;
    private String desc;

    private TargetUserType(int type, String desc) {
        this.desc = desc;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
