package com.nrpc.common.util;

import com.nrpc.common.constants.ClientConstants;

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
 * 2018年02月08日 下午5:17   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
public class BeanUtils {

	/**
	 * 检测注解是否合理
	 * @param annotationList
	 * @return
	 */
	public static boolean checkClientAnnotion(Annotation[]annotationList)throws Exception
	{

		if(annotationList==null||annotationList.length==0)
			throw new Exception("annotation annotationList");

		for(Annotation annotation:annotationList)
		{

			String name=annotation.annotationType().getName();

			//检测到客户端的注解名称
			if(name.equals(ClientConstants.CLIENT_INTERFACE_NAME))
			{

			}

		}

		return false;


	}
}