package com.dj.web.exception;

/**
 * Created by daijie on 2017-8-4.
 */
public class BusinessException extends Exception {

	private static final long serialVersionUID = 7196480721222237361L;
		
	private String msg;

	private String code;

	/**
	 * Created by daijie on 2017-8-4.
	 */
	 public BusinessException(String errorcode){
	    this.code = errorcode;
	  }

	 /**
	  * 
	  * @Title: BusinessException
	  * @Description: 构造方法,传入业务异常码和业务异常信息
	  * @param errorcode 异常码
	  * @param msg 异常信息
	  * @throws
	  */
	 public BusinessException(String errorcode, String msg){
	    this.code = errorcode;
	    this.msg = msg;
	  }
	 
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
