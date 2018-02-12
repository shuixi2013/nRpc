package com.nrpc.test.service.impl;

import com.nrpc.server.annotations.NRPCService;
import com.nrpc.test.service.TestService;
import org.springframework.stereotype.Service;

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
 * 2018年02月11日 下午5:57   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */

@Service
public class TestServiceImpl implements TestService {

	@NRPCService(serviceName = "doTest")
	@Override public void doTest(String name, String email) {

	}
	@NRPCService(serviceName = "doTest2")
	@Override public String doTest2(String name, String email) {
		return null;
	}
}
