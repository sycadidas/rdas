package com.discovery.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.Eum.Constant;
import com.common.utils.ProTool;
import com.discovery.service.serviceInte.UserService;
import com.founder.bbc.util.redis.RedisUtil;

@Controller
@RequestMapping("/check")
public class CheckUser  {
	protected final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private UserService userService;
	
	@RequestMapping("/user.action")
	@ResponseBody
    public String checkUser(String _username,String _password,String _smsCode,HttpServletRequest req,HttpServletResponse res){
		try {
			//1，校验传的参数是否为空
			if(_username!=null&&_password!=null&&_smsCode!=null){
				//校验用户信息
				String systemPassword = new ProTool().getUserInfo(Constant.CTString.UserInfoProperties.get_code(), _username,"password");
				//取校验手机验证
				String key = "npop_rads_" + _username;
				String code = RedisUtil.get(key, String.class);
				if(systemPassword == null||"".equals(systemPassword)){
					return "usernameErr";
				}else if(!_password.equals(systemPassword)){
					return "passwordErr";
				}else if(!_smsCode.equals(code)){
					return "codeErr";
				}else{
					putUserToSession(_username,req);
					return "true";
				}
			}else{
				return "false";
			}
		} catch (Exception e) {
			logger.error("登录失败原因", e);
			e.printStackTrace();
			return "false";
		}
    }

	private void putUserToSession(String _username,HttpServletRequest req) {
		String file = Constant.CTString.UserInfoProperties.get_code();
		String userTooken = new ProTool().getUserInfo(file,_username,"password")+"&"+
				            new ProTool().getUserInfo(file,_username,"phoneNo")+"&"+
				            new ProTool().getUserInfo(file,_username,"authority");
		RedisUtil.set(req.getSession().getId(), userTooken, 30*60);
	}
	
	
	/**
	 * 发送验证码
	 * @param _username
	 * @return
	 */
	@RequestMapping("/getPhoneNo.action")
	@ResponseBody
	public String getPhoneNo(String userName) {
 		try {
 			String phoneNo = new ProTool().getUserInfo(Constant.CTString.UserInfoProperties.get_code(), userName,"phoneNo");
 			if(phoneNo!=null&&!"".equals(phoneNo)){
 				phoneNo = phoneNo.substring(0, 3)+"****"+phoneNo.substring(7,phoneNo.length());
 			}
 			return phoneNo;
 		} catch (Exception e) {
 			logger.error(e.getMessage(), e);
 			return "false";
 		}

 	}
	
	
	/**
	 * 发送验证码
	 * @param _username
	 * @return
	 */
	@RequestMapping("/getCode.action")
	@ResponseBody
	public String sendMessageVerficode(String userName) {
 		try {
 			Boolean flag = userService.sendMessageForShopUser(userName);
 			if (flag) {
 				 return "true";
 			} else {
 				 return "false";
 			}
 		} catch (Exception e) {
 			logger.error(e.getMessage(), e);
 			return "error";
 		}

 	}
	
	@RequestMapping("/main.action")
	public String main(){
		return "main";
	}
	
	@RequestMapping("/logonOut.action")
	@ResponseBody
	public String logonOut(HttpServletRequest req){
		HttpSession session = req.getSession();
		session.invalidate();
		RedisUtil.del(session.getId());
		return "true";
	}
}
