package com.common.Eum;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Constants {
	
	
	public static final String DIAMOND_RDAS_GROUP = "NPOP_RDAS";
	
	public static final String DIAMOND_RDAS_PROPERTIES_DATA = "PROP_DATA";
	
	public static final String DIAMOND_RDAS_SYS_CONF = "SYS_CONF";
	
	/** 本机ip **/
	public static final String localIP = getLocalIP();

	private static String getLocalIP(){
		
		try {
			return InetAddress.getByName(InetAddress.getLocalHost().getHostName()).getHostAddress();
			
		} catch (UnknownHostException e) {
			throw new RuntimeException("没有获取到本地IP:当前机器名配置不是IP,请检查HOST配置");
		}
		
	}
}
