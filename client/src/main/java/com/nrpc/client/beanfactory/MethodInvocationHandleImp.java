package com.nrpc.client.beanfactory;

import com.nrpc.client.connect.SocketPoolMap;
import com.nrpc.client.connect.SocketPoolMap.SocketPool;
import com.nrpc.client.constans.BeanConstants;
import com.nrpc.client.utils.BeanUtils;
import com.nrpc.client.utils.LogHandler;
import com.nrpc.client.vo.HostInfo;
import com.nrpc.client.vo.MessageInfo;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 *
 *
 *
 *构造接口的代理类
 *
 * <p>
 * 修改历史:                                                                                    &lt;br&gt;
 * 修改日期             修改人员        版本                     修改内容
 * --------------------------------------------------------------------
 * 2018年02月08日 下午3:38   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
public class MethodInvocationHandleImp implements InvocationHandler,LogHandler{

	// 目标接口
	private Class<?>[] target;

	/**
	 * 服务位置名称
	 */
	private String locationName;


	public MethodInvocationHandleImp(Class<?>[] target,String locationName) {
		super();
		this.target = target;
		this.locationName=locationName;
	}
	@Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		//根据请求策略选择联机的ip
		HostInfo hostInfo=BeanUtils.getHostInfo(locationName);

		Socket socket=getSocket(hostInfo);

		if(socket==null)
		{
			NRPC_CLIENT_LOGGER.info("nrpc get socket null");
			return null;
		}


		return sendReq(method,args,socket);
	}

	/**
	 * 发送请求
	 * @param method    远端方法名称
	 * @param args      远端参数
	 * @param socket
	 * @return
	 * @throws Exception
	 */
	public Object sendReq(Method method,Object[]args,Socket socket)throws Exception
	{
		BufferedSink sink= Okio.buffer(Okio.sink(socket));
		BufferedSource source= Okio.buffer(Okio.source(socket));

		//构造消息
		MessageInfo messageInfo=new MessageInfo();
		messageInfo.buildMessage(method.getName(),args);

		//发送消息
		sink.write(messageInfo.getSrcByte());
		sink.flush();

		source.skip(4);

		int byteLength=source.readByte();

		byte[]response=source.readByteArray(byteLength);

		return BeanUtils.toObject(response);



	}

	public Object getProxy() {
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), target, this);
	}

	/**
	 * 根据hostInfo从连接池种获取连接
	 * @param hostInfo
	 */
	private Socket getSocket(HostInfo hostInfo)
	{
		Socket socket=null;

		try {

			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(hostInfo.getIp());
			stringBuilder.append(BeanConstants.COLON);
			stringBuilder.append(hostInfo.getPort());

			//192.168.89.32:8898
			String socketKey = stringBuilder.toString();
			//may be bug here ,thread 1 and thread 2 find null ,and they all creat new SocketPool
			if (SocketPoolMap.instance.getPoolConcurrentHashMap().get(socketKey) == null) {
				SocketPool socketPool = new SocketPool(hostInfo);
				SocketPoolMap.instance.getPoolConcurrentHashMap().put(socketKey, socketPool);

				socket = SocketPoolMap.instance.getPoolConcurrentHashMap().get(socketKey).borrowObject();
			}
		}catch (Exception e)
		{
			   NRPC_CLIENT_LOGGER.error("get socket null",e);
		}
		return socket;
	}
}
