package com.nrpc.test;

import com.nrpc.client.beanfactory.BeanFactory;
import com.nrpc.test.service.ClientService;

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
 * 2018年02月24日 下午4:43   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
public class TestClient {
	public static void main(String[]args) throws Exception
	{

		ClientService clientService= BeanFactory.getInstance().getService(ClientService.class);
		clientService.doTest("a","bc");

	}
}
