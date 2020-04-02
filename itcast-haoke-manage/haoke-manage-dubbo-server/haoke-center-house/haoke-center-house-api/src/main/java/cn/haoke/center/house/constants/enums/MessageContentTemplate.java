package cn.haoke.center.house.constants.enums;

public class MessageContentTemplate {

    /***
     * 预约通知
     */
    public static final String APPOINTMENT_APPLY = "%s预约%s看%s";

    /***
     * 看房通知（通过）
     */
    public static final String SEE_HOUSE_NOTICE_PASS = "您预约%s看%s已通过，请按时前往";
    /***
     * 看房通知（未通过）
     */
    public static final String SEE_HOUSE_NOTICE_NO_PASS = "您预约%s看%s未通过，请再次联系房东进行确认";

    /***
     * 取消看房，由用户取消
     */
    public static final String USER_CANCEL_SEE_HOUSE = "%s已取消%s看%s的预约申请";
    /***
     * 取消看房，由房东取消
     */
    public static final String OWNER_CALCEN_SEE_HOUSE = "您预约%s看%s已被房东取消，请您再次联系房主";
}
