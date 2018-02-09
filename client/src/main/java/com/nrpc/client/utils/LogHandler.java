package com.nrpc.client.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * 2018年02月09日 下午2:23   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
public interface LogHandler {

	/**
	 * 输出和记录程序info，warn，error信息，供后续查看使用
	 */
	static final Logger nrpcClientLogger = LoggerFactory.getLogger("nrpcClientLogger");

	/**
	 * 业务调用日志输出，记录业务方法调用情形，一个调用一条日志，输出格式按照checklist统计格式输出
	 */
	static final Logger nrpcClientApiLogger = LoggerFactory.getLogger("nrpcClientApiLogger");



}
