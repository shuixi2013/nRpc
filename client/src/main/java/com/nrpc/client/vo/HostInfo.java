package com.nrpc.client.vo;

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
 * 2018年02月09日 上午9:21   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
public class HostInfo {
	/**
	 * ip地址
	 */
	private String ip;
	/**
	 * 端口
	 */
	private Integer port;


	private RequestStrategy requestStrategy;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public RequestStrategy getRequestStrategy() {
		return requestStrategy;
	}

	public void setRequestStrategy(RequestStrategy requestStrategy) {
		this.requestStrategy = requestStrategy;
	}
}
