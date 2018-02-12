package com.nrpc.server.utils;

import com.nrpc.server.vo.MethodProvider;
import org.springframework.web.context.WebApplicationContext;

import java.util.concurrent.ConcurrentHashMap;

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
 * 2018年02月12日 上午10:35   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
public class MethodFactory implements LogHandler{
	/**
	 * 存储所有方法名称和调用bean的映射
	 */
	public static ConcurrentHashMap<String,MethodProvider>methodMap=new ConcurrentHashMap<String, MethodProvider>(32);

	public static void init(WebApplicationContext cx)
	{
		try {

			MethodFactory.methodMap = AnnotationParseUtils.getAllNrpcMethodProvider(cx);
		}catch (Exception e)
		{
			NRPC_SERVER_LOGGER.error("MethodFactory init",e);
		}
	}
}
