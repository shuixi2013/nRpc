package com.nrpc.server.vo;

import java.io.Serializable;

/**
 *
 *
 *
 *
 *
 * <p>
 * 修改历史:                                                                                    &lt;br&gt;
 * 修改日期             修改人员        版本                     修改内容
 * --------------------------------------------------------------------
 * 2018年03月01日 下午3:59   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
public class NRPCServerResponse implements Serializable {

	/**
	 * 相应码
	 * 0
	 * 10，服务端方法不存在
	 * 20，返回值没有实现序列化接口
	 */
	private int code=0;

	/**
	 * 调用结果
	 */
	private Object body;
	/**
	 *
	 */
	private String message;

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

