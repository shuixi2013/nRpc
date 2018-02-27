package com.nrpc.server.connect;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

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
 * 2018年02月11日 下午5:05   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
public class SimpleChannelHandler extends ChannelInitializer<SocketChannel> {
	@Override protected void initChannel(SocketChannel socketChannel) throws Exception {
		socketChannel.pipeline().addLast("nettyServerHandler",new NettyServerHandler());

	}
}
