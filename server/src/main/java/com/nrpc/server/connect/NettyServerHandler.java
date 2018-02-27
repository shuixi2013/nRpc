package com.nrpc.server.connect;

import com.google.common.collect.Maps;
import com.nrpc.server.utils.CommonUtils;
import com.nrpc.server.utils.LogHandler;
import com.nrpc.server.vo.RequestMessageInfo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import okio.Buffer;

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
public class NettyServerHandler extends ChannelInboundHandlerAdapter implements LogHandler{
	@Override public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		Map<String,String>stringStringMap= Maps.newHashMap();

		RequestMessageInfo requestMessageInfo= parseMessage(req);

		System.out.println("The time server receive order : " + requestMessageInfo.getMethodName());

		ByteBuf resp = Unpooled.copiedBuffer("response code is 200".getBytes());
		ctx.write(resp);
	}

	/**
	 * 解析消息
	 * |nrpc|methodLength|getDeviceId|2 args1Length|args1|args2Length|args2
	 * @param bytes
	 */
	private RequestMessageInfo parseMessage(byte[]bytes)
	{
		RequestMessageInfo requestMessageInfo=new RequestMessageInfo();
		Buffer buffer=new Buffer();
		try {
			buffer.write(bytes);

			//跳过开头的nrpc
			buffer.skip(4);

			//方法名称长度
			int methodNameLength=buffer.readByte();

			byte[]methodBytes=buffer.readByteArray(methodNameLength);

			String methodNameStr=new String(methodBytes);

			//参数长度
			int totalArgsLength=buffer.readByte();

			Object[]argsObjArray=new Object[totalArgsLength];
			//分别读取每个参数
			for(int i=0;i<totalArgsLength;i++)
			{
				int argsLength=buffer.readByte();
				byte[]argsBytes=buffer.readByteArray(argsLength);
				//将byte反序列化为obj
				Object object= CommonUtils.toObject(argsBytes);

				argsObjArray[i]=object;
			}

			requestMessageInfo.setArgs(argsObjArray);
			requestMessageInfo.setMethodName(methodNameStr);







		}catch (Exception e)
		{
			NRPC_SERVER_LOGGER.error("",e);
		}

		return requestMessageInfo;

	}

	@Override public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

		NRPC_SERVER_LOGGER.error("NettyServerHandler exception",cause);
		ctx.close();
		super.exceptionCaught(ctx, cause);
	}

	@Override public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
	}
}
