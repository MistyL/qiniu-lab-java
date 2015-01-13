package com.qiniu.lab.service.error;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 封装回复的错误处理
 * */
public class QiniuError extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 *            网络正常得到的回复中的错误信息
	 * @param cause
	 *            网络故障等造成的异常
	 * */
	public QiniuError(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 获取回复中的error信息，在有回复的时候，错误信息格式如下： {"error":"detailed error message"}
	 * */
	public String getError() throws JSONException {
		String message = this.getMessage();
		JSONObject errorObject = new JSONObject(message);
		return errorObject.getString("error");
	}

}
