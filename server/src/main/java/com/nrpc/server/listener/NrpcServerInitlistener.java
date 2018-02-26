package com.nrpc.server.listener;

import com.nrpc.server.connect.SimpleChannelHandler;
import com.nrpc.server.utils.LogHandler;
import com.nrpc.server.utils.MethodFactory;
import com.nrpc.server.utils.PropertiesReaderUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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


		// 这里不是通过依赖注入，而是直接从容器中拿
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());

		//将服务端注册的方法和被调用的bean关联起来，默认bean使用的spring中的bean容器管理起来
		MethodFactory.init(ctx);

		new Thread(new Runnable() {
			@Override public void run() {
				   initServer();
			}
		}).start();

	}

	@Override public void contextDestroyed(ServletContextEvent sce) {

		NRPC_SERVER_LOGGER.info("NrpcServerInitlistener  close");
	}

	/**
	 * 初始化后端的netty服务
	 */
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
			String port= PropertiesReaderUtil.getStrFromBundle("nrpc.netty.port");
			//发生异步连接操作
			ChannelFuture f = b.bind( Integer.valueOf(port)).sync();

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
