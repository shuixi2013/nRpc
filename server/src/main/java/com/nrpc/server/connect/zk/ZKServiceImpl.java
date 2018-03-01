package com.nrpc.server.connect.zk;

import com.nrpc.server.utils.LogHandler;
import com.nrpc.server.utils.PropertiesReaderUtil;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

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
 * 2018年03月01日 上午10:45   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
public class ZKServiceImpl implements LogHandler{
	private static  ZooKeeper zookeeper=null;

	static {

		  initZk();
	}

	/**
	 * 初始化zk，包括建立连接在zk下面创建临时节点
	 */
	public static void initZk()
	{
		try {

			String zkConnectString= PropertiesReaderUtil.getStrFromBundle("zkConnectString", PropertiesReaderUtil.ZK_CONFIG_URI);
			String zkSessionTimeout=PropertiesReaderUtil.getStrFromBundle("zkSessionTimeOut", PropertiesReaderUtil.ZK_CONFIG_URI);

			zookeeper=new ZooKeeper(zkConnectString,Integer.valueOf(zkSessionTimeout),null);

			createZNode();

		}catch (Exception e)
		{
			NRPC_SERVER_LOGGER.error("",e);
		}
	}

	/**
	 * 创建临时节点
	 */
	public static void createZNode()throws Exception
	{
		String parentDir=PropertiesReaderUtil.getStrFromBundle("nrpc.productName");
		String childNode=PropertiesReaderUtil.getStrFromBundle("nrpc.serviceName");

		zookeeper.create("/nrpc",parentDir.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		zookeeper.create("/nrpc/"+parentDir,childNode.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

	}

}
