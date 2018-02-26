package com.nrpc.client.beanfactory;

import com.google.common.base.Strings;
import com.nrpc.client.utils.BeanUtils;
import com.nrpc.client.vo.RequestStrategy;

import java.lang.annotation.Annotation;

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
 * 2018年02月08日 下午3:33   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
public class BeanFactory {

	private BeanFactory() {

	}

	/**
	 * 工厂模式的单例
	 * @return
	 */
	public static BeanFactory getInstance() {
		return BeanFactoryTemp.instance;
	}

	/**
	 * 根据接口名称生成代理类
	 * @param t
	 * @param <T>
	 * @return
	 */
	public <T> T getService(Class<T> t) throws Exception {

		Annotation[] annotations = t.getAnnotations();


		//解析产品、服务名称
		String locationName=BeanUtils.parseAnnotion(annotations);

		if(Strings.isNullOrEmpty(locationName))
			return null;

		//使用默认的请求策略
		return getService(t,new RequestStrategy());

	}

	/**
	 * 根据请求的接口生成代理类
	 * @param t
	 * @param strategy 请求策略
	 * @param <T>
	 * @return
	 * @throws Exception
	 */
	public <T> T getService(Class<T> t,RequestStrategy strategy) throws Exception {
		Annotation[] annotations = t.getAnnotations();


		//从注解上解析产品、服务名称
		String locationName=BeanUtils.parseAnnotion(annotations);

		if(Strings.isNullOrEmpty(locationName))
			return null;

		//生成代理类
		MethodInvocationHandleImp methodInvocationHandleImp = new MethodInvocationHandleImp(t,locationName);

		T proxy = (T) methodInvocationHandleImp.getProxy();

		return proxy;


	}



	public static class BeanFactoryTemp {
		public static BeanFactory instance = new BeanFactory();
	}
}
