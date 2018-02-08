package com.nrpc.client.beanfactory;

import com.nrpc.client.utils.BeanUtils;

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
		Class<T>[] src = new Class[1];
		src[0] = t;
		Annotation[] annotations = t.getAnnotations();

		//注解不合法
		if (!BeanUtils.checkClientAnnotion(annotations))
			return null;

		MethodInvocationHandleImp methodInvocationHandleImp = new MethodInvocationHandleImp(src);
		T t1 = (T) methodInvocationHandleImp.getProxy();

		return t1;
	}

	public static class BeanFactoryTemp {
		public static BeanFactory instance = new BeanFactory();
	}
}
