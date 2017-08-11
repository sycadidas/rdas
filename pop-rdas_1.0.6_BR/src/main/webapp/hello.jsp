<!DOCTYPE HTML>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"  isELIgnored="false"%>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page session="true" %>
<html>
<head>
<meta charset="utf-8">
<title>LOGIN</title>
<link rel="stylesheet" href="styles/bootstrap.min.css">
<link rel="stylesheet" href="styles/style.css" />
<link rel="stylesheet" type="text/css" href="styles/btn.css"/>
<script type="text/javascript" src="scripts/jquery-1.7.1.min.js"></script>
</head>
<body>
<div class="bg">
	<div class="con">
		<div class="logo"></div>
		<div class="login">login</div>
		<div class="wel">国美在线数据修复系统</div>
		<div style="padding: 10px 20px 10px; ">
	<form class="bs-example bs-example-form" role="form">
		<!-- 用户名 -->
		<div class="input-group input-group-lg">
			<span class="input-group-addon"><img src="images/user.png"/></span>
			<input id="username" type="text" class="form-control h_49" placeholder="请输入用户名" onblur="getPhoneNo();">
		</div><br>
		<!-- 密码 -->
		<div class="input-group input-group-lg">
			<span class="input-group-addon"><img src="images/key.png"/></span>
			<input  id="password" type="password" class="form-control h_49" placeholder="请输入密码">
		</div><br>
		<!-- 手机验证码 -->
		<div id="captcha" class="yanzheng" style="display:block;">
			<div class="input-group input-group-lg">
			<span class="input-group-addon">手机号：</span>
			<input type="text" class="form-control wid " id="userMobile" value="" disabled="true">
		    </div><br>
			<div class="input-group input-group-lg in-b">
			<span class="input-group-addon"><img src="images/yanzheng.png"/></span>
			<input  class="form-control h_49" id="smsCode" placeholder="请输入手机验证码">
		</div>
			<input value="获取验证码" id="btnGetCode" class="button button-pill button-primary sub" type="button" onclick="getCode()">
		</div>
        <a onclick="doLogin()" class="button button-block button-pill button-action button-glow button-large h_49" id="login">登录</a>
	</form>
</div> 
	</div>
	
</div>
</body>
<script type="text/javascript">
function doLogin(){
flag = validateusercode();
	if(flag==true){
		var username = $("#username").val();
		var password = $("#password").val();
		var smsCode= $("#smsCode").val();
		 $.ajax({  
			     url:"<%=basePath %>check/user.action",  
			      data:{  
			    	  "_username" : username,
			    	  "_password" :password,
			    	  "_smsCode":smsCode
			      },  
			     type:'post',
			     cache:false,  
			     /* dataType:'json', 坑*/
			     success:function (data) {
			    	if(data==='true'){
			    		 window.location="<%=basePath %>check/main.action";
			    	}else if(data === 'usernameErr'){
			    		 alert("用户名输入有误");
		    		 }else if(data === 'passwordErr'){
		    		     alert("密码输入有误");
		    		 }else if(data=='codeErr'){
		    			 alert("手机验证码输入有误");
		    		 }else{
		    			 alert("登录失败");	
		    		 }
			    }
			 }); 
	}
}
function validateusercode(){
	if($("#username").val()!=''&&$("#password").val()!=''){
		return true;
	}else{
		alert('用户名或密码不能为空');
		return false;
	}
}


//获取输入账号的手机号码
function getPhoneNo(){
	var d = new Date().getTime();
	var user_name = $("#username").val();
	if("" != user_name){
		$.ajax({
	        type:"post",
			url:"<%=basePath %>check/getPhoneNo.action?v="+d,
			data:"userName="+user_name,
			cache:false,
			//dataType:"json",
			success:function(data){
					if(typeof(data)!="undefined" && "" != data && null != data){
						$('#userMobile').val(data);
					}else{
						//登录按钮置灰、获取短信验证码按钮置灰
						$('#userMobile').val("");
					}
			   },
			   error:function(){
				  alert("服务器正忙，请稍候再试");
			   }
		});
	}else{
		$('#userMobile').val("");
	}
}


function getCode(){
	    var d = new Date().getTime();
		var username = $("#username").val();
		if(username==''){
			 alert("请先输入用户名");
			 return;
		}
		//没有手机号码，不允许提交
	 	var mobile = $("#userMobile").val();
	 	if($.trim(mobile) == '') {
	 		alert("该用户没有对应的手机号!");
	 		return;
	 	}
			$.ajax({  
			     url:"<%=basePath %>check/getCode.action?v="+d,
			     data:"userName="+username,
			     type:'post',
			     cache:false,  
			     //dataType:'json',
			     success:function (data) {
			    	 try {
							if (data==='true') {
								if(typeof(interval)!="undefined"){
									if (interval){
										clearInterval(interval);
									}
								}
								time = parseInt(60);
								//一秒一跳
								interval = setInterval("jiancecode()",1000);
							} else {
								alert("发送短信失败!");
								$("#btnGetCode").bind("click",getCode);
							}
						} catch (e) {
							alert("请稍后再获取!");
						}
			    }
			 }); 
}



	function jiancecode() {
		if (time < 1) {
			if (interval)
				clearInterval(interval);
			$("#btnGetCode").removeAttr("disabled");
			$("#btnGetCode").val("获取验证码");
			$("#btnGetCode").bind("click",getCode);
		} else {
			$("#btnGetCode").attr("disabled","disabled");
			$("#btnGetCode").val((--time)+"秒后重新取");
		}
	}
</script>
</html>