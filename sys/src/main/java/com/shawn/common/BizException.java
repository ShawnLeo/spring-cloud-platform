package com.shawn.common;

public class BizException extends Exception{

    public BizException(){

    }

    public BizException(String message){
        super(message);
    }

    public BizException(String message, Throwable e){
        super(message,e);
    }

}
