package com.nrpc.client.connect;

import com.nrpc.client.vo.HostInfo;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.net.Socket;

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
 * 2018年02月09日 上午10:04   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
public class SocketFactory extends BasePooledObjectFactory<Socket> {

	private HostInfo hostInfo;

	public SocketFactory(HostInfo hostInfo) {
		this.hostInfo = hostInfo;
	}

	@Override public Socket create() throws Exception {
		Socket socket=new Socket(this.hostInfo.getIp(),this.hostInfo.getPort());
		return socket;
	}

	@Override public PooledObject<Socket> wrap(Socket socket) {
		return new DefaultPooledObject<Socket>(socket);
	}

	@Override public void destroyObject(PooledObject<Socket> p) throws Exception {
		Socket socket = p.getObject();
		socket.close();
		super.destroyObject(p);
	}

	@Override public boolean validateObject(PooledObject<Socket> p) {
		return super.validateObject(p);
	}
}
