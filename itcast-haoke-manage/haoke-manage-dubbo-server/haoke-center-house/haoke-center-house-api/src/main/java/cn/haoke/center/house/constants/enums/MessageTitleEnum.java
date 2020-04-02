package cn.haoke.center.house.constants.enums;

public enum  MessageTitleEnum {
    SEE_HOUSE_NOTICE("看房通知"),
    CANCEL_SEE_HOUSE("取消看房"),
    APPOINTMENT_APPLY("预约申请 "),
    HOUSE_EXPIRE("房屋到期");
    private String title;
    private MessageTitleEnum(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
