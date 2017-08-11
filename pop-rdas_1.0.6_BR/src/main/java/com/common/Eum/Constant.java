package com.common.Eum;


/**
 * 枚举变量
 * @author sunyanchen
 */
public class Constant {
	
	public static enum CTString{
		FrontPage("pop-rdas/hello.jsp"),//登录首页
		Login_Url("/check/user.action"),//登录Url
		VERCode_Url("/check/getCode.action"),//发送验证码
		GetPhoneNo("/check/getPhoneNo.action"),//发送验证码
		USER("user"),//session中当前登录用户的key
		UserInfoProperties("/userInfo.properties"),
		Admin_role("Role_admin"),
		User_role("Role_user"),
		NoAuthority("pop-rdas/notice.jsp");
		CTString(String code){
			_code = code;
		}
		
		String _code;
		
		public String get_code() {
			return _code;
		}


		public void set_code(String _code) {
			this._code = _code;
		}
	}
	//操作权限列表
	public  static String AuthList(){
		return "queryDeliveyInfo.action/sendOrderXml.action/send.action/alter.action";
	}
}
