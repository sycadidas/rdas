<!DOCTYPE HTML>
<%@page import="com.founder.bbc.util.redis.RedisUtil"%>
<%@page import="java.text.*"%>
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
<title>主页面</title>
<link rel="stylesheet" href="/pop-rdas/styles/innerStyle.css" />
</head>
<body>
	<%@ include file="common/head.jsp"%><!-- 页头 -->
	<div class="mainCon ">
		<div class="crumbs"></div>
		<div class="clearfloat content_box">
			<%@ include file="common/nav1.jsp"%><!-- 导航 -->
			<div class="content_r">
				<p class="hello">您好，!欢迎来到国美在线--数据修复系统!</p>
			</div>
		</div>
	</div>
	<%@ include file="common/foot.jsp"%><!-- 页足 -->
</body>
<script type="text/javascript" src="/pop-rdas/scripts/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="/pop-rdas/scripts/slider.js"></script>
<script type="text/javascript">

</script>
</html>