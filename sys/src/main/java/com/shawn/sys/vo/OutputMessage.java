package com.shawn.sys.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.shawn.sys.util.MessageCode;
import com.shawn.sys.util.MessageError;

import java.io.Serializable;


@JsonInclude(Include.NON_NULL)
public class OutputMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private Object payload;

	private MessageCode resultCode = MessageCode.SUCCESS;
	private MessageError errorCode;
	private String errorMessage;

	/**
	 * 处理结果
	 * 
	 * @return
	 */
	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	/**
	 * 业务处理状态码
	 * 
	 * @return
	 */
	public MessageCode getResultCode() {
		return resultCode;
	}

	public void setResultCode(MessageCode resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * 业务处理错误码
	 * 
	 * @return
	 */
	public MessageError getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(MessageError errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * 业务处理错误信息
	 * 
	 * @return
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
