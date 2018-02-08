package com.test;

import com.nrpc.client.beanfactory.BeanFactory;
import org.junit.Test;

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
 * 2018年02月08日 下午2:50   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
public class ClientMain {
	@Test
	public void doTest()throws Exception
	{
		STest sTest= BeanFactory.getInstance().getService(STest.class);
		sTest.test("s","sd");








	}
	public static class STestImpl implements STest{
		@Override public void test2(String name, String id) {

		}

		@Override public void test(String name, String id) {

		}
	}
}
