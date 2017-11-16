package com.shawn.sys.exception;

/**
 * Created by wanglu-jf on 17/7/20.
 */
public class ValidationException extends Exception {
    private String code;
    private String message;

    public ValidationException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ValidationException(String message, Throwable cause) {
        super(cause);
        this.message = message;
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
