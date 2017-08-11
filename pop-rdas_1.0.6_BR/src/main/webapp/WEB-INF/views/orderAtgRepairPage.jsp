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
	<meta charset="UTF-8">
	<title>订单管理</title>
	<link rel="stylesheet" href="/pop-rdas/styles/innerStyle.css" />
</head>
<body>
	<%@ include file="common/head.jsp"%><!-- 页头 -->
	<div class="mainCon ">
		<div class="crumbs"></div>
		<div class="clearfloat content_box">
			<%@ include file="common/nav2.jsp"%><!-- 导航 -->
			<div class="content_r">
				<ul>
					<li>
						<h3 class="content_r_h3">ATG</h3>
						<div class="content_r_box">
				         	<ul class="clearfloat tab_head">
								<li class="active">与ATG交互状态修复</li>
							</ul>
							<div class="tab_body">
								<div class="tab1 active">
									<ul class="clearfloat tab_head3">
										<li class="active"><input type="radio" name="DRGdata_type" checked="checked"><label>回滚状态后重新推送</label></li>
										<li><input type="radio" name="DRGdata_type"><label>仅推送报文</label></li>
										<li><input type="radio" name="DRGdata_type"><label>回滚订单状态</label></li>
									</ul>
									<div class="tab_body3">
										<div id="box1" class="active tab3"><!-- 回滚状态后重新推送 -->
											<ul class="c_data">
												<li>
													<span><i>*&nbsp;</i>推送状态：</span>
													<div class="alignL w80">
														<input  id="win1inp1" type="button" class="align1 menugo1 white" value="请选择">
														<ul class="triCon2 menugone1">
															<li><a href="##" title="PP">PP</a></li>
															<li><a href="##" title="EX">EX</a></li>
															<li><a href="##" title="DL">DL</a></li>
															<li><a href="##" title="RT">RT</a></li>
															<li><a href="##" title="RCC">RCC</a></li>
															<li><a href="##" title="DFC">DFC</a></li>
															<li><a href="##" title="RM">RM</a></li>
															<li><a href="##" title="RWA">RWA</a></li>
															<li><a href="##" title="RCP">RCP</a></li>
															<li><a href="##" title="RBGC">RBGC</a></li>
															<li><a href="##" title="RRF">RRF</a></li>
															<li><a href="##" title="RFL">RFL</a></li>
														</ul>
													</div>
												</li>
												<li>
													<span><i>*&nbsp;</i>相关单号：</span>
													<input id="win1inp2" type="text" placeholder="正向单状态填写sub_order_id,逆向单状态填写order_no">
												</li>
												<li>
													<span><i>*&nbsp;</i>店铺信息：</span>
													<input id="win1inp3" type="text" placeholder="填写格式为shopNumber/ownShop">
												</li>
												<li id="TrackingInfo">
													<span><i>*&nbsp;</i>货运信息：</span>
													<input id="win1inp4" type="text" placeholder="填写格式为运单号/承运商编码/承运商名称">
												</li>
											</ul>
											<div class="btn_box">
												<a onclick="send('alter_send')" href="javascript:;">推送</a>
												<a onclick="resetWin1()" href="javascript:;" class="reset">清空</a>
											</div>
										</div>
										<div id="box2" class="tab3"><!-- 仅发报文 -->
											<ul class="c_data">
												<li>
													<span><i>*&nbsp;</i>推送状态：</span>
													<div class="alignL w80">
														<input id="win2inp1" type="button" class="align1 menugo1 white" value="请选择">
														<ul class="triCon2 menugone1">
															<li><a href="##" title="PP">PP</a></li>
															<li><a href="##" title="EX">EX</a></li>
															<li><a href="##" title="DL">DL</a></li>
															<li><a href="##" title="RT">RT</a></li>
															<li><a href="##" title="RCC">RCC</a></li>
															<li><a href="##" title="DFC">DFC</a></li>
															<li><a href="##" title="RM">RM</a></li>
															<li><a href="##" title="RWA">RWA</a></li>
															<li><a href="##" title="RCP">RCP</a></li>
															<li><a href="##" title="RBGC">RBGC</a></li>
															<li><a href="##" title="RRF">RRF</a></li>
														</ul>
													</div>
												</li>
												<li>
													<span><i>*&nbsp;</i>相关单号：</span>
													<input id="win2inp2" type="text" placeholder="正向单状态填写sub_order_id,逆向单状态填写order_no">
												</li>
												<li>
													<span><i>*&nbsp;</i>店铺信息：</span>
													<input id="win2inp3" type="text" placeholder="填写格式为shopNumber/ownShop">
												</li>
												<li id="TrackingInfo1">
													<span><i>*&nbsp;</i>货运信息：</span>
													<input id="win2inp4" type="text" placeholder="格式为运单号/承运商编码/承运商名称">
												</li>
											</ul>
											<div class="btn_box">
												<a href="javascript:;" onclick="send('only_send')">推送</a>
												<a href="javascript:;" onclick="resetWin2()" class="reset">清空</a>
											</div>
										</div>
										<div id="box3" class="tab3"><!-- 回滚订单状态 -->
											<ul class="c_data">
												<li>
													<span><i>*&nbsp;</i>相关单号：</span>
													<input id="win3inp1" type="text" placeholder="正向单填写subOrderId，逆向单填写orderNo">
												</li>
												<li>
													<span><i>*&nbsp;</i>目标状态：</span>
													<div class="alignL w80">
														<input id="win3inp2" type="button" class="align1 menugo1 white" value="请选择">
														<ul class="triCon2 menugone1">
														    <li><a href="##" title="PR">PR</a></li>
															<li><a href="##" title="PP">PP</a></li>
															<li><a href="##" title="EX">EX</a></li>
															<li><a href="##" title="RSC">RSC</a></li>
															<li><a href="##" title="RM">RM</a></li>
															<li><a href="##" title="RWA">RWA</a></li>
															<!-- <li><a href="##" title="DL">DL</a></li>新增状态
															<li><a href="##" title="RT">RT</a></li>
															<li><a href="##" title="RCC">RCC</a></li>
															<li><a href="##" title="DFC">DFC</a></li>
															<li><a href="##" title="RCP">RCP</a></li>
															<li><a href="##" title="RBGC">RBGC</a></li>
															<li><a href="##" title="RRF">RRF</a></li>
															<li><a href="##" title="RFL">RFL</a></li> -->
														</ul>
													</div>
												</li>
											</ul>
											<div class="btn_box">
												<a href="javascript:;" onclick="send2()">执行</a>
												<a href="javascript:;" onclick="resetWin3()" class="reset">清空</a>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<%@ include file="common/foot.jsp"%><!-- 页足 -->

<script type="text/javascript" src="/pop-rdas/scripts/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="/pop-rdas/scripts/slider.js"></script>
<script type="text/javascript">
function resetWin1(){
	$("#win1inp1").val("请选择");
	$("#win1inp2").val("");
	$("#win1inp3").val("");
	$("#win1inp4").val("");
}
function resetWin2(){
	$("#win2inp1").val("请选择");
	$("#win2inp2").val("");
	$("#win2inp3").val("");
	$("#win2inp4").val("");
}
function resetWin3(){
	$("#win3inp1").val("");
	$("#win3inp2").val("请选择");
}
//推送的方法
function send(sign){
	if(sign==='only_send'){
		flag = emptyCheck1();
	}else{
		flag = emptyCheck();
	}
	if(flag==true){
		if(sign==='only_send'){
			var status = $("#win2inp1").val();
			var order_no = $("#win2inp2").val();
			var shop_info= $("#win2inp3").val();
			var tracking_info= $("#win2inp4").val();
		}else{
			var status = $("#win1inp1").val();
			var order_no = $("#win1inp2").val();
			var shop_info= $("#win1inp3").val();
			var tracking_info= $("#win1inp4").val();
		}
		 $.ajax({  
			     url:"<%=basePath %>order/send.action",  
			      data:{  
			    	  "status":status,
			    	  "order_no":order_no,
			    	  "shop_info":shop_info,
			    	  "tracking_info":tracking_info,
			    	  "sign":sign
			      },  
			     type:'post',
			     cache:false,  
			     async: true,
			     success:function (data) {
			    	if(data==='true'){
			    		alert("推送成功");
			    	}else if(data==='false'){
			    		alert("系统报错了");
			    	}
			    }
			 }); 
		}
	}
	
	
	//修改订单状态
	function send2(){
		flag = emptyCheck2();
		if(flag==true){
			var orderNo= $("#win3inp1").val();
			var status = $("#win3inp2").val();
			 $.ajax({  
				     url:"<%=basePath %>order/alter.action",  
				      data:{  
				    	  "orderNo":orderNo,
				    	  "status":status,
				      },  
				     type:'post',
				     cache:false,
				     async: true,
				     /* dataType:'json', 坑*/
				     success:function (data) {
				    	if(data==='true'){
				    		alert("回滚成功");
				    	}else if(data==='false'){
				    		alert("系统报错了，请检查所选状态值是否和订单类型匹配");
				    	}
				    }
				 }); 
		}
	}
	
	function emptyCheck(){
		if($("#win1inp1").val()==="请选择"){
			alert("请选择推送状态")
			return false;
		}else if($("#win1inp2").val()===""){
			alert("请填写单号信息");
			return false;
		}else if($("#win1inp3").val()===""){
			alert("请填写店铺信息");
			return false;
		}else{
			return true;
		}
	}
	function emptyCheck1(){
		if($("#win2inp1").val()==="请选择"){
			alert("请选择推送状态")
			return false;
		}else if($("#win2inp2").val()===""){
			alert("请填写单号信息");
			return false;
		}else if($("#win2inp3").val()===""){
			alert("请填写店铺信息");
			return false;
		}else{
			return true;
		}
	}
	function emptyCheck2(){
		if($("#win3inp1").val()===""){
			alert("请填写相关单号");
			return false;
		}else if($("#win3inp2").val()==="请选择"){
			alert("请填写目标状态");
			return false;
		}else{
			return true;
		}
	}
	//货运信息的显示隐藏
	$("#win1inp1").next().find('li').click(
			function hideInpView(){
				var s = $("#win1inp1").val();
				if(s==='EX'||s==='DL'||s==='RT'){
					$("#TrackingInfo").show();
				}else{
					$("#TrackingInfo").hide();
				}
			}	
	);
	$("#win2inp1").next().find('li').click(
			function hideInpView(){
				var s = $("#win2inp1").val();
				if(s==='EX'||s==='DL'||s==='RT'){
					if(s==='EX'){
						$("#TrackingInfo1").show();
						$("#win2inp4").attr('placeholder','格式为运单号/承运商编码/承运商名称/warehouseID');
					}else{
						$("#TrackingInfo1").show();
						$("#win2inp4").attr('placeholder','格式为运单号/承运商编码/承运商名称');
					}
				}else{
					$("#TrackingInfo1").hide();
				}
			}	
	);
	
	//全局的ajax处理结果前置函数 用于没有权限的时候跳转到提示页面
	jQuery.ajaxSetup({
	    contentType : "application/x-www-form-urlencoded;charset=utf-8",
	    complete : function(XMLHttpRequest, textStatus) {
	        var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus"); // 通过XMLHttpRequest取得响应头，sessionstatus，
	        if (sessionstatus == "noAuthon") {
	            //没有权限跳转到指定页面
	            window.location.replace("<%=basePath %>notice.jsp");
	        }
	    }
	}); 
</script>
</body>
</html>