package com.shawn.sys.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.ui.Model;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ResponseMessage implements Serializable {

	private static final long serialVersionUID = 986200217071030933L;

	public static final int SC_FAIL = -1;
	public static final int SC_SUCCESS = 1;

	private int statusCode;
	private String statusMessage;
	/** 
	 * data : 数据
	 */
	private Object data;

	public void fail(String msg) {
		statusCode = SC_FAIL;
		statusMessage = msg;
	}

	public void success() {
		statusCode = SC_SUCCESS;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	/**
	 * @return 获取 data 数据
	 */
	public Object getData() {
		return data;
	}

	/**
	 * 设置 data 数据
	 *
	 * @param data
     */
	public void setData(Object data) {
		this.data = data;
	}
	
	public void addAttributeTo(Model model) {
		if (data != null) {
			model.addAttribute("resp", data);
		}
	}

}
