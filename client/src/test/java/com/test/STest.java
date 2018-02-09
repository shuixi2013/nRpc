package com.test;

import com.nrpc.client.annotations.NRPCInterface;

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
 * 2018年02月08日 下午2:48   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
@NRPCInterface(productName = "s2",serviceName = "dache")
public interface STest {
	public void test(String name,String id);
	public void test2(String name,String id);
}
