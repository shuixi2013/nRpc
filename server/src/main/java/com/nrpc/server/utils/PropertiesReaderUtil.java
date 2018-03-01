package com.nrpc.server.utils;

import java.util.ResourceBundle;

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
 * 2018年02月12日 上午11:17   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
public class PropertiesReaderUtil {
	/**
	 * 基础配置位置
	 */
	public static final String BASE_CONFIG_URI="nrpc/baseConfig";
	/**
	 * zk集群的相关配置
	 */
	public static final String ZK_CONFIG_URI="nrpc/zkConfig";


	public static String getStrFromBundle(String key)
	{
		return    getStrFromBundle(key,BASE_CONFIG_URI);
	}
	public static String getStrFromBundle(String key,String path)
	{
		String configValue=ResourceBundle.getBundle(BASE_CONFIG_URI).getString(key);

		return configValue;
	}
}
