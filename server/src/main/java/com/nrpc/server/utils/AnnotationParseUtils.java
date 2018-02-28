package com.nrpc.server.utils;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.nrpc.server.annotations.NRPCService;
import com.nrpc.server.constants.CommonConstants;
import com.nrpc.server.vo.MethodProvider;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
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
 * 2018年02月11日 下午5:17   shikai.liu     1.0   初始化创建
 * </p>
 *
 * @author shikai.liu
 * @version 1.0
 * @since JDK1.7
 */
public class AnnotationParseUtils implements LogHandler{

	/**
	 * 从spring的容器里面获取注册的方法
	 * 遍历容器找到所有的bean，遍历所有的bean找到nrpcService注解的方法。
	 * @param ctx
	 * @return
	 */
	public static ConcurrentHashMap<String,MethodProvider> getAllNrpcMethodProvider(WebApplicationContext ctx)
	{
		ConcurrentHashMap<String,MethodProvider>methodMap= new ConcurrentHashMap<String, MethodProvider>(32) ;

		//获取容器内所有bean的名称
		String[]strings=ctx.getBeanDefinitionNames();

		for(String name:strings)
		{
			Object beanObj=ctx.getBean(name);

			//遍历所有对象的方法，找到nrpc注解的方法
			List<MethodProvider> methodProviderList=findNrpcAnnnotationMehod(beanObj);

			if(methodProviderList.size()!=0)
			{
				for(MethodProvider methodProvider:methodProviderList) {

					String key = methodProvider.getServiceName();

					methodMap.put(key, methodProvider);
				}
			}
		}




		return methodMap;
	}

	/**
	 * 从对象所在的类种找到包含注解的方法
	 * @param object
	 * @return
	 */
	public static List<MethodProvider> findNrpcAnnnotationMehod(Object object)
	{
		List<MethodProvider>methodProviderList = Lists.newArrayList();
		MethodProvider methodProvider=null;

		Method[]methods=object.getClass().getMethods();

		for(Method item:methods)
		{

			Annotation[]annotations=item.getAnnotations();
			for(Annotation annotationItem:annotations)
			{
				//从方法的注解上找到nrpcService的注解
				if(annotationItem.annotationType().getName().equals(CommonConstants.CLIENT_INTERFACE_NAME))
				{
					NRPCService nrpcService=(NRPCService)annotationItem;

					//检测注册的名称是否为空
					if(Strings.isNullOrEmpty(nrpcService.serviceName()))
						return null;
					else
					{

						methodProvider=new MethodProvider();
						methodProvider.setMethod(item);
						methodProvider.setObject(object);
						methodProvider.setServiceName(nrpcService.serviceName());
						methodProviderList.add(methodProvider);
					}
				}
			}
		}

		return methodProviderList;
	}
	public static List<Class<?>> findContext()
	{
		List<Class<?>> classList = new ArrayList<Class<?>>();



		Enumeration<URL> dirs = null;

		//获取当前目录下面的所有的子目录的url
		try
		{
			dirs = Thread.currentThread().getContextClassLoader().getResources("/");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		while (dirs.hasMoreElements())
		{
			URL url = dirs.nextElement();

			//得到但钱url的类型
			String protocol = url.getProtocol();

			//如果当前类型是文件类型
			if ("file".equals(protocol))
			{
				//获取包的物理路径
				String filePath = null;
				try
				{
					filePath = URLDecoder.decode(url.getFile(), "UTF-8");
				}
				catch (UnsupportedEncodingException e)
				{
					e.printStackTrace();
				}

			//	filePath = filePath.substring(1);
				getFilePathClasses(filePath,classList);
			}
		}


		return classList;
	}
	private static void getFilePathClasses(String filePath,List<Class<?>> classList)
	{
		Path dir = Paths.get(filePath);

		DirectoryStream<Path> stream = null;
		try
		{
			//获得当前目录下的文件的stream流
			stream = Files.newDirectoryStream(dir);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		for(Path path : stream)
		{
			String fileName = String.valueOf(path.getFileName());

			String className = fileName.substring(0, fileName.length() - 6);

			Class<?> classes = null;
			try
			{
				classes = Thread.currentThread().getContextClassLoader().loadClass( className);
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}

			//判断该注解类型是不是所需要的类型
			if (null != classes && null != classes.getAnnotation(NRPCService.class))
			{
				//把这个文件加入classlist中
				classList.add(classes);
			}
		}
	}

}
