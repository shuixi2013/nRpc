package com.nrpc.server.vo;

import java.lang.reflect.Method;

/**
 *
 *
 * 方法最终的调用映射
 *
 *
 * <p>
 * 修改历史:                                                                                    &lt;br&gt;
 * 修改日期             修改人员        版本                     修改内容
 * --------------------------------------------------------------------
 * 2018年02月12日 上午10:04   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
public class MethodProvider {
	/**
	 * 最终被调用service的方法
	 */
	private Method  method;
	/**
	 * 最终被调用bean的对象
	 */
	private Object  object;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * 注解NrpcService的serviceName
	 */

	private String serviceName;

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
}
