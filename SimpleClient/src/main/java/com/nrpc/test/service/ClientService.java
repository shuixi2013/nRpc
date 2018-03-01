package com.nrpc.test.service;

import com.nrpc.client.annotations.NRPCInterface;
import com.nrpc.server.vo.NRPCServerResponse;

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
 * 2018年02月24日 下午4:46   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
@NRPCInterface(productName = "didi",serviceName = "dache",interfaceName = "test")
public interface ClientService {
	public NRPCServerResponse doTest(String name,String email);
}
