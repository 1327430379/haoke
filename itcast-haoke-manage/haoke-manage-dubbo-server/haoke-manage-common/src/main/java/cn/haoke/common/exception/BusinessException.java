package cn.haoke.common.exception;

public class BusinessException extends Exception{

    public BusinessException(){

    }
    public BusinessException(String msg){
        super(msg);
    }
}
