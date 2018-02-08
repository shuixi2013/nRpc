package com.nrpc.client.beanfactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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
 * 2018年02月08日 下午3:38   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
public class MethodInvocationHandleImp implements InvocationHandler{

	// 目标对象
	private Class<?>[] target;


	public MethodInvocationHandleImp(Class<?>[] target) {
		super();
		this.target = target;
	}
	@Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		System.out.println(200);
		return null;
	}

	public Object getProxy() {
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), target, this);
	}
}
