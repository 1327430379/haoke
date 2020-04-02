package cn.haoke.common.vo;

import java.io.Serializable;

public class RestResponse<T> implements Serializable {

    private int resultCode;
    private String resultMsg;
    private T data;
    public static final int successCode = 200;
    public static final int failCode = 500;
    public static final RestResponse SUCCESS = new RestResponse<>(successCode);
    public static final RestResponse<Void> VOID = new RestResponse<>();
    public RestResponse() {
        this.resultCode = successCode;
    }

    public RestResponse(T data) {
        this.resultCode = successCode;
        this.resultMsg = "success";
        this.data = data;
    }

    public RestResponse(int resultCode, String resultMsg) {
        this.resultMsg = resultMsg;
        this.resultCode = resultCode;

    }
    public RestResponse(int resultCode,T data){
        this.resultCode = resultCode;
        this.data = data;
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
