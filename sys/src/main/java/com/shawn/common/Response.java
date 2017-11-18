package com.shawn.common;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    // 响应头
    private ResponseHeader header;

    // 响应体
    private T body;

    public Response() {
        header = new ResponseHeader();
    }

    public static <T> Response<T> success(T body) {
        Response response = new Response();
        response.getHeader().setCode(RetCode.SUCCESS.getCode());
        response.setBody(body);
        return response;
    }

    public static Response error(String message) {
        Response response = new Response();
        response.getHeader().setCode(RetCode.INTERNALEXCEP.getCode());
        response.getHeader().setMessage(message);
        return response;
    }

    public static Response exception(Throwable e) {
        Response response = new Response();
        response.getHeader().setCode(RetCode.INTERNALEXCEP.getCode());
        response.getHeader().setMessage(e.getMessage());
        return response;
    }

    public static <T> Response<T> response(String code, String message, T body) {
        Response response = new Response();
        response.getHeader().setCode(code);
        response.getHeader().setMessage(message);
        response.setBody(body);
        return response;
    }

    public static <T> Response<T> response(String code, String message) {
        Response response = new Response();
        response.getHeader().setCode(code);
        response.getHeader().setMessage(message);
        return response;
    }

    public ResponseHeader getHeader() {
        return header;
    }

    public void setHeader(ResponseHeader header) {
        this.header = header;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

}
