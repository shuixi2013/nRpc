package com.nrpc.client.utils;

import com.google.common.base.Strings;
import com.nrpc.client.annotations.NRPCInterface;
import com.nrpc.client.constans.BeanConstants;
import com.nrpc.client.vo.HostInfo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
 * 2018年02月08日 下午5:28   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
public class BeanUtils implements LogHandler {

	/**
	 * 从注解中解析出服务、产品名称
	 * @param annotationList
	 * @return
	 */
	public static String parseAnnotion(Annotation[]annotationList)throws Exception
	{

		if(annotationList==null||annotationList.length==0)
			throw new Exception("annotation annotationList is null");

		for(Annotation annotation:annotationList)
		{

			String annotationName=annotation.annotationType().getName();

			//检测到客户端的注解名称
			if(annotationName.equals(BeanConstants.CLIENT_INTERFACE_NAME))
			{
				NRPCInterface nrpcInterface=(NRPCInterface)annotation;
				//获得产品和服务方法
				String productName=nrpcInterface.productName();
				String serviceName=nrpcInterface.serviceName();

				if(Strings.isNullOrEmpty(productName)||Strings.isNullOrEmpty(serviceName))
					throw new Exception("productName or serviceName is null");

				StringBuilder stringBuilder=new StringBuilder();
				stringBuilder.append(productName);
				stringBuilder.append(BeanConstants.COLON);
				stringBuilder.append(serviceName);

				return stringBuilder.toString();


			}

		}

		return null;


	}




	/**
	 * 对象转数组
	 * @param obj
	 * @return
	 */
	public static byte[] toByteArray (Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes = bos.toByteArray ();
			oos.close();
			bos.close();
		} catch (IOException ex) {
			nrpcClientLogger.error("toByteArray error",ex);
		}
		return bytes;
	}

	public static HostInfo getHostInfo(String locationName)
	{
		return new HostInfo();
	}

	/**
	 * 数组转对象
	 * @param bytes
	 * @return
	 */
	public static Object toObject (byte[] bytes) {
		Object obj = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream (bytes);
			ObjectInputStream ois = new ObjectInputStream (bis);
			obj = ois.readObject();
			ois.close();
			bis.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return obj;
	}
}
