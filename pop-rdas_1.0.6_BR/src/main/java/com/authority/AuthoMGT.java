package com.authority;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.common.Eum.Constant;
import com.common.dto.User;
import com.founder.bbc.util.redis.RedisUtil;
/**
 * 访问限制过滤器
 * @author sunyanchen
 */
public class AuthoMGT extends HttpServlet implements Filter {

	private static final long serialVersionUID = 1L;

	@Override
	public void doFilter(ServletRequest req, ServletResponse rep,FilterChain fc) throws IOException, ServletException {
		
		HttpServletResponse response = (HttpServletResponse)rep ;    
        HttpServletRequest request=(HttpServletRequest)req;
        
        boolean isajax = ajaxDofilterSessionNull(request,response);
        
        String sid = request.getSession().getId();
//      response.setHeader("SET-COOKIE", "HttpOnly"); //防止js脚本获取Cookie信息
        String userTooken = RedisUtil.get(sid);
        String url=request.getRequestURI();
        //userTooken为空只允许访问首页和登录方法
         if(userTooken==null)  
         {  
            //判断获取的路径不为空且不是访问登录页面或执行登录操作时跳转  
            if(url!=null && !url.equals("") && ( !url.contains(Constant.CTString.Login_Url.get_code())&&( !url.contains(Constant.CTString.VERCode_Url.get_code()))&&( !url.contains(Constant.CTString.GetPhoneNo.get_code())) && !url.contains(Constant.CTString.FrontPage.get_code())))  
            {  
                response.sendRedirect(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+Constant.CTString.FrontPage.get_code());  
                return ;  
            }   
        //用户登录后的验证-取出用户权限 比对当前url是否属于该权限范围 不是的话 跳转到无权访问的页面
        }else{
        	RedisUtil.expire(sid, 300);
        	boolean authon = isAuthon(url,userTooken);
        	if(authon==false){
        		if(isajax!=true){
        			response.sendRedirect(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/"+Constant.CTString.NoAuthority.get_code());
        			return;
        		}else{
        			response.setHeader("sessionstatus", "noAuthon");
        			return;
        		}
        	}
        	
        }
        fc.doFilter(req, rep);  
        return;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	//判断是否是ajax请求
	private static boolean ajaxDofilterSessionNull(HttpServletRequest request,HttpServletResponse response){
	        boolean isAjax = false;
	        if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equals("XMLHttpRequest")) { 
	            // ajax请求
	            isAjax = true;
	        } 
	        return isAjax;
	    }
	//url鉴权方法-判断用户是否有当前url的访问权限
	private static boolean isAuthon(String url,String userTooken){
		Boolean flag = true;
		String[] auList = Constant.AuthList().split("/");
		if(url.contains(auList[0])||url.contains(auList[1])||url.contains(auList[2])||url.contains(auList[3])){
			if(!(Constant.CTString.Admin_role.get_code().equals(userTooken.split("&")[2]))){
				flag = false;
    		}else{
    			flag = true;
    		}
		}else{
			flag = true;
		}
		return flag;
	}
	

}
