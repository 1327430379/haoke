package cn.haoke.common.vo;

import java.io.Serializable;

public class RestResponse<T> implements Serializable {

    private int resultCode;
    private String resultMsg;
    private T data;
    public static final RestResponse SUCCESS = new RestResponse();

    public RestResponse() {
    }

    public RestResponse(T data) {
        this.resultCode = 0;
        this.resultMsg = "success";
        this.data = data;
    }

    public RestResponse(int resultCode, String resultMsg) {
        this.resultMsg = resultMsg;
        this.resultCode = resultCode;

    }

    public RestResponse(int resultCode, String resultMsg, T data) {
        this.resultMsg = resultMsg;
        this.resultCode = resultCode;
        this.data = data;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RestResponse{" +
                "resultCode=" + resultCode +
                ", resultMsg='" + resultMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
