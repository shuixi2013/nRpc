package com.nrpc.client.connect;

import com.nrpc.client.utils.LogHandler;
import com.nrpc.client.vo.HostInfo;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

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
 * 2018年02月09日 上午10:58   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
public class SocketPoolMap {

	public static SocketPoolMap instance=new SocketPoolMap();

	//32初始化的socketPool的个数，默认为32台机器数
	private ConcurrentHashMap<String,SocketPool>poolConcurrentHashMap=new ConcurrentHashMap<String, SocketPool>(32);

	public ConcurrentHashMap<String, SocketPool> getPoolConcurrentHashMap() {
		return poolConcurrentHashMap;
	}

	public void setPoolConcurrentHashMap(ConcurrentHashMap<String, SocketPool> poolConcurrentHashMap) {
		this.poolConcurrentHashMap = poolConcurrentHashMap;
	}

	/**
	 * socket连接池
	 */
	public static class SocketPool implements LogHandler{
		private GenericObjectPool<Socket> socketPool;



		public SocketPool(HostInfo hostInfo) {
			// 初始化对象池配置
			GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();


			socketPool = new GenericObjectPool<Socket>(new SocketFactory(hostInfo), poolConfig);
		}

		public Socket borrowObject()throws Exception {
			Socket socket=null;
			try {
				 socket = socketPool.borrowObject();
			}catch (Exception e)
			{
				NRPC_CLIENT_LOGGER.error("SocketPool borrowObject error",e);
				throw new Exception(e);

			}
			return socket;
		}

		public void returnObject(Socket socket) {
			socketPool.returnObject(socket);
		}
	}
}
