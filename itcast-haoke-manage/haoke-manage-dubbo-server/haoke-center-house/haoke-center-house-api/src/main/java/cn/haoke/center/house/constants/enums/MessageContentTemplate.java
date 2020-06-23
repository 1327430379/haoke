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

    /**
     * 合同内容模板
     */
    public static final String CONTRACT_CONTENT_TEMPLATE = "您租赁的房屋期限由%s到%s，甲方应于每月按时向乙方收取房租,租赁期满或合同解除后，甲方有权收回，乙方应按照原状返还房屋及附属物品,甲乙双方应对房屋和附属物品、设备设施及水电使用等情况进行验收，结清各自应当承担的费用";
}
