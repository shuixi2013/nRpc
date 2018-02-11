package com.nrpc.server.listener;

import com.nrpc.server.connect.SimpleChannelHandler;
import com.nrpc.server.utils.LogHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
 * 2018年02月11日 下午4:40   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
public class NrpcServerInitlistener implements ServletContextListener, LogHandler {
	@Override public void contextInitialized(ServletContextEvent sce) {
		initServer();

	}

	@Override public void contextDestroyed(ServletContextEvent sce) {

		NRPC_SERVER_LOGGER.info("NrpcServerInitlistener  close");
	}

	public static void initServer()
	{
		//配置客户端的NIO线程组
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group)
				.channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new SimpleChannelHandler());


		try {
			//发生异步连接操作
			ChannelFuture f = b.connect("127.0.0.1", 8787).sync();

			//等待客户端链路关闭
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {

			NRPC_SERVER_LOGGER.error("NrpcServerInitlistener InterruptedException error",e);
		} finally {
			//优雅退出，释放NIO线程组
			group.shutdownGracefully();
		}
	}

}
