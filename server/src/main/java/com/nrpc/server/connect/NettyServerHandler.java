package com.nrpc.server.connect;

import com.google.common.collect.Maps;
import com.nrpc.server.constants.CommonConstants;
import com.nrpc.server.utils.CommonUtils;
import com.nrpc.server.utils.LogHandler;
import com.nrpc.server.utils.MethodFactory;
import com.nrpc.server.vo.MethodProvider;
import com.nrpc.server.vo.NRPCServerResponse;
import com.nrpc.server.vo.RequestMessageInfo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import okio.Buffer;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;

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
 * 2018年02月11日 下午3:53   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter implements LogHandler {
	@Override public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		Map<String, String> stringStringMap = Maps.newHashMap();

		//解析调用的方法，调用的参数
		RequestMessageInfo requestMessageInfo = parseMessage(req);

		//执行调用
		NRPCServerResponse result= doExecute(requestMessageInfo);

		NRPC_SERVER_LOGGER.info("NRPCServerResponse  code is :"+result.getCode());

		ByteBuf resp = Unpooled.copiedBuffer(CommonUtils.toByteArray(result));
		ctx.write(resp);
	}

	/**
	 * 解析消息
	 * |nrpc|methodLength|getDeviceId|2 args1Length|args1|args2Length|args2
	 * @param bytes
	 */
	private RequestMessageInfo parseMessage(byte[] bytes) {
		RequestMessageInfo requestMessageInfo = new RequestMessageInfo();
		Buffer buffer = new Buffer();
		try {
			buffer.write(bytes);

			//跳过开头的nrpc
			buffer.skip(4);

			String methodName = parseMethodName(buffer);

			//参数长度
			int totalArgsLength = buffer.readByte();

			Object[] argsObjArray = new Object[totalArgsLength];
			//分别读取每个参数
			for (int i = 0; i < totalArgsLength; i++) {
				int argsLength = buffer.readByte();
				byte[] argsBytes = buffer.readByteArray(argsLength);
				//将byte反序列化为obj
				Object object = CommonUtils.toObject(argsBytes);

				argsObjArray[i] = object;
			}

			requestMessageInfo.setArgs(argsObjArray);
			requestMessageInfo.setMethodName(methodName);

		} catch (Exception e) {
			NRPC_SERVER_LOGGER.error("", e);
		}

		return requestMessageInfo;

	}

	/**
	 * 通过反射调用方法
	 * @param requestMessageInfo
	 * @return
	 * @throws Exception
	 */
	public NRPCServerResponse doExecute(RequestMessageInfo requestMessageInfo)
	{
		NRPCServerResponse nrpcServerResponse=new NRPCServerResponse();
		try {
			String methodName = requestMessageInfo.getMethodName();
			//获取方法执行的method,Obj
			MethodProvider methodProvider = MethodFactory.methodMap.get(methodName);

			if (methodProvider == null) {
				nrpcServerResponse.setCode(10);
				return nrpcServerResponse;
			}

			Method method = methodProvider.getMethod();
			Object object = methodProvider.getObject();
			Object result = method.invoke(object, requestMessageInfo.getArgs());

			if(!(result instanceof Serializable))
				nrpcServerResponse.setCode(20);

			nrpcServerResponse.setBody(result);
		}catch (Exception e)
		{
			NRPC_SERVER_LOGGER.error("doExecute error",e);
		}

		return nrpcServerResponse;
	}

	/**
	 * 从byte[]中解析方法名称,格式是interfaceName:methodName
	 * @param buffer
	 * @return
	 * @throws Exception
	 */
	public String parseMethodName(Buffer buffer)throws Exception
	{
		//方法名称长度
		int interfaceLength=buffer.readByte();

		byte[]interfaceBytes=buffer.readByteArray(interfaceLength);

		String interfaceStr=new String(interfaceBytes);

		//方法名称长度
		int methodNameLength=buffer.readByte();

		byte[]methodBytes=buffer.readByteArray(methodNameLength);

		String methodNameStr=new String(methodBytes);

		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append(interfaceStr);
		stringBuilder.append(CommonConstants.COLON);
		stringBuilder.append(methodNameStr);
		return stringBuilder.toString();
	}

	@Override public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

		NRPC_SERVER_LOGGER.error("NettyServerHandler exception",cause);
		ctx.close();
		super.exceptionCaught(ctx, cause);
	}

	@Override public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
		ctx.flush();
	}
}
