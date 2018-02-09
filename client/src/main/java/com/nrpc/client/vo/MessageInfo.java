package com.nrpc.client.vo;

import com.nrpc.client.constans.BeanConstants;
import com.nrpc.client.utils.BeanUtils;
import com.nrpc.client.utils.LogHandler;
import okio.Buffer;
import okio.BufferedSink;

/**
 *
 *
 * ************************消息格式***********************
 * 远端接口：public String getDeviceId(String args1,String args2)
 * 消息格式：|nrpc|methodLength|getDeviceId|2 args1Length|args1|args2Length|args2
 * <p>
 * 修改历史:                                                                                    &lt;br&gt;
 * 修改日期             修改人员        版本                     修改内容
 * --------------------------------------------------------------------
 * 2018年02月09日 下午3:00   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
public class MessageInfo implements LogHandler{
	private byte[]srcByte;

	public byte[]getSrcByte()
	{
		return this.srcByte;
	}

	public  MessageInfo buildMessage(String methodName,Object[]args)
	{

		BufferedSink sink = new Buffer();
		try {

			//消息头写入nrpc
			sink.write(BeanConstants.NRPC.getBytes());

			byte[] methodNameBytes = methodName.getBytes();

			int methodNameLength=methodNameBytes.length;

			//写入方法名称的长度
			sink.writeByte(methodNameLength);

			//写入方法名称
			sink.write(methodNameBytes);

			int argsLength = args.length;

			if(argsLength!=0) {

				//写入参数的个数
				sink.writeByte(argsLength);

				//遍历参数的个数，先写入占的长度，再写入byte
				for (Object o : args) {
					byte[]sr=BeanUtils.toByteArray(o);

					sink.writeByte(sr.length);
					sink.write(sr);
				}
			}else sink.writeByte(0);

			this.srcByte=sink.buffer().readByteArray();

		}catch (Exception e)
		{
			nrpcClientLogger.error("buildMessage error",e);
		}
		return this;
	}
}
