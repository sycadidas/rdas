package com.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * 读取配置文件的工具类
 * @author sunyanchen
 */
public class ProTool {
	/**
	 * 该静态方法用于获取一个用户的具体属性值
	 * @param filePath 文件名 在枚举变量类中
	 * @param username 用户名
	 * @param target 要获取什么 ？可选值 password、phoneNo，authority
	 * @return 要获取的值
	 */
	private static final Logger log = Logger.getLogger(ProTool.class);
	
	public   String getUserInfo(String filePath,String username,String target ){
		String userEntry = null;
		String value = null ;
		Properties prop = new Properties(); 
	    InputStream in = this.getClass().getResourceAsStream(filePath); 
	        try { 
	        	prop.load(in);
	            userEntry = prop.getProperty(username).trim();
	            if(userEntry!=null){
	            	String[] temp$ = userEntry.split("/");
	            	if(temp$!=null && temp$.length == 3){
	            		if("password".equals(target)){
	    	            	value = temp$[0];
	    	            }else if("phoneNo".equals(target)){
	    	            	value = temp$[1];
	    	            }else{
	    	            	value = temp$[2];
	    	            }
	            	}
	            }
	        } catch (Exception e) { 
	        	//用户名&密码输入错误，工具类肯定都获取不到数值，属于正常现象 记一下log就行了 可以把异常吃掉。
	            log.error("没有获取到值");
	        }
	        return value;
	}
//	public static void main(String[] args) {
//		System.out.println(getUserInfo("/userInfo.properties","sunyanchen","password"));
//	}
}
