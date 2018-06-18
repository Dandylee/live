package com.mama.dandy.exception;

public class BusinessException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7656721656320045400L;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	private String code;
	
	private String msg;
	
	public BusinessException(String code,String msg){
		this.msg=msg;
		this.code=code;
	}
	
	public BusinessException(){}

}
