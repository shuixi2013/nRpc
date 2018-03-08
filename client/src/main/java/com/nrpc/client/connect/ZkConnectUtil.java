package com.nrpc.client.connect;

import com.nrpc.client.utils.LogHandler;
import com.nrpc.client.utils.PropertiesReaderUtil;
import com.nrpc.client.vo.HostInfo;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;
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
 * 2018年03月01日 下午3:41   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
public class ZkConnectUtil implements LogHandler{

	private  ZooKeeper zooKeeper=null;

	/**
	 * 存储zk节点的map
	 */
	private ConcurrentHashMap<String,List<String>>nodeMap= new ConcurrentHashMap<String, List<String>>(32);

	/**
	 *单利模式
	 */
	public static ZkConnectUtil instance=new ZkConnectUtil();

	private ZkConnectUtil() {

		doConnectZk();
	}


	/**
	 * 初始化的时候连接zk集群
	 */
	private  void doConnectZk()
	{
		try {
			String zkConnectString= PropertiesReaderUtil.getStrFromBundle("zkConnectString", PropertiesReaderUtil.ZK_CONFIG_URI);
			String zkSessionTimeout=PropertiesReaderUtil.getStrFromBundle("zkSessionTimeOut", PropertiesReaderUtil.ZK_CONFIG_URI);

			zooKeeper=new ZooKeeper(zkConnectString,Integer.valueOf(zkSessionTimeout),null);




		}catch (Exception e)
		{
			NRPC_CLIENT_LOGGER.error("",e);
		}
	}

	public  HostInfo getHostInfo(String locationName)
	{
		List<String>ipAndPortList=this.nodeMap.get(locationName);
		//从本地缓存获取节点信息
		if(ipAndPortList==null||ipAndPortList.size()==0)
			return null;




		return new HostInfo("127.0.0.1",8989);
	}

	/**
	 * 从远端zk拉取节点信息
	 * @param locationName
	 * @return
	 */
	private HostInfo getHostInfoFromZK(String locationName)
	{
		HostInfo hostInfo=null;
		try {


			byte[] dataBytes = zooKeeper.getData(locationName, new ZKWatcher(this.nodeMap), null);
			String res=new String(dataBytes);
		}catch (Exception e)
		{
			NRPC_CLIENT_LOGGER.error("",e);
		}
		return hostInfo;
	}


	public static class ZKWatcher  implements Watcher
	{
		public ZKWatcher(ConcurrentHashMap concurrentHashMap) {
			this.concurrentHashMap = concurrentHashMap;
		}

		private ConcurrentHashMap concurrentHashMap;

		public ConcurrentHashMap getConcurrentHashMap() {
			return concurrentHashMap;
		}

		public void setConcurrentHashMap(ConcurrentHashMap concurrentHashMap) {
			this.concurrentHashMap = concurrentHashMap;
		}

		@Override public void process(WatchedEvent watchedEvent) {

			if (watchedEvent.getType() == null || "".equals(watchedEvent.getType())) {
				return;
			}
			watchedEvent.getPath()


		}
	}

}
