package cn.haoke.common.constants.enums;

public enum ExceptionCode {
    NO_AUTHENTICATION(401,"未认证"),
    LOGIN_INFO_INVALID(401,"登录信息失效");

    private Integer code;
    private String msg;

    ExceptionCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
