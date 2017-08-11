<!DOCTYPE HTML>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
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
						<h3 class="content_r_h3">DRG</h3>
						<div class="content_r_box">
				         	<ul class="clearfloat tab_head">
								<li class="active">与DRG交互状态修复</li>
							</ul>
							<div class="tab_body">
								<form id="sendForm" action="" name="queryForm" method="post" enctype="multipart/form-data">
								<div class="tab1 active">
<!-- 									<ul class="clearfloat tab_head3"> -->
<!-- 										<li class="active"><input type="radio" name="DRGdata_type" checked="checked"><label>更改状态及推报文</label></li> -->
<!-- 										<li><input type="radio" name="DRGdata_type"><label>仅推报文</label></li> -->
<!-- 										<li><input type="radio" name="DRGdata_type"><label>仅更改状态</label></li> -->
<!-- 									</ul> -->
									<div class="tab_body3">
										<div class="active tab3">
											<ul class="c_data">
												<li>
													<span><i>*&nbsp;</i>推送接口：</span>
													<div class="alignL w80">
														<input type="button" class="align1 menugo1 white" value="代运订单推送(B4.3与B3.5)">
														<ul class="triCon2 ">
															<li><a href="##" title="啦1">代运订单推送(B4.3与B3.5)</a></li>
														</ul>
													</div>
												</li>
												<li>
													<span><i>*&nbsp;</i>店铺编码：</span>
													<input type="text" name="shopNo" id="shopNo" onChange="hiddenDiv();"/>
												</li>
												<li>
													<span><i>*&nbsp;</i>配送单号：</span>
													<input type="text" name="subOrderId" id="subOrderId" onChange="hiddenDiv();">
													<span class="btn_box" style="margin-left:20px;"><a href="#" onclick="queryDeliveyInfo();">查询发货信息</a></span>
												</li>
											</ul>
											<div id="delievInfo" style="display:none;">
												<ul class="c_data">
													<li>
													<span><i>*&nbsp;</i>物流公司：</span>
													<div class="alignL w80">
														<input type="button" id="logistics" class="align1 menugo1 white" value="请选择" tag="">
														<ul class="triCon2 menugone1">
<%-- 															<c:forEach items="${lcCorps }" var="lc"> --%>
<%-- 											  					<li id="${lc.logisticsId }abcba${lc.logisticsName }abcba${lc.logisticsFlag }"><a href="##" title="啦1">${lc.logisticsName}</a></li> --%>
<%-- 															</c:forEach> --%>
														</ul>
													</div>

													</li>
													<li>
														<span><i>*&nbsp;</i>包裹件数：</span>
														<input type="text" name="orderNum" id="orderNum" value="1" onKeyup="this.value=this.value.replace(/[^0-9a-z]/ig,'');"
																				onBlur="this.value.replace(/[^0-9a-z]/ig,'');value=value.replace(/[^0-9a-z]/ig,'');"/>
													</li>
													<li>
														<span><i>*&nbsp;</i>运单号：</span>
														<input type="text" name="trackingNo" id="trackingNo" value="${order.trackingNo}"/>
														&nbsp;&nbsp;&nbsp;&nbsp;说明：多个运单号用“，”号分隔开。包裹件数与运单数一致
													</li>
													<li>
														<span><i>*&nbsp;</i>发货地：</span>
														<div class="alignL w80">
														    <!-- 下面这个输入框是用来存储模拟的select的当前值得的 -->
															<input type="button" id="trackingAddressId" class="align1 menugo1 white" value="请选择" tag="">
															<ul class="triCon2 menugone1">
<%-- 																<c:forEach items="${listaddress}" var="address">  --%>
<%-- 																	<li id="${address.id}"><a href="##" title="啦1">${address.address_short_name}</a></li> --%>
<%-- 															    </c:forEach> --%>
															</ul>
														</div>
													</li>
													<li>
														<span><i>*&nbsp;</i>客户手机号：</span>
														<input type="text" name="mobile" id="mobile" value="${order.mobile}" onKeyup="this.value=this.value.replace(/[^0-9a-z]/ig,'');"
																					onBlur="this.value.replace(/[^0-9a-z]/ig,'');value=value.replace(/[^0-9a-z]/ig,'');"/>
													</li>
												</ul>
											</div>
											<div class="btn_box" id="send" style="display:none;">
												<a href="javascript:send();">推送</a>
												<a href="javascript:clear();" class="reset">清空</a>
											</div>
										</div>
									</div>
								</div>
								</form>
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
<%-- <script type="text/javascript" src="<%=path%>/js/json2.js"></script> --%>
<script type="text/javascript">

$(function(){

	/*下拉框*/
	
	$('.alignL input').click(function () {   
		$(".triCon2").hide();
		$(this).next().show();
	});
	$('.triCon2').on('click','li',function () {    
		var tri = $(this).find('a').html();
		var tmp = $(this).attr("id");
		$(this).parent().parent().find('.align1').val(tri);
		$(this).parent().parent().find('.align1').attr("tag",tmp);
		$('.triCon2').hide();
	});
	$(document).click(function(){    
		$(".triCon2").hide();
	});
	$('.alignL').click(function (event) {    
		event.stopPropagation();
	});

});

/**
 * 查询发货信息
 */
function queryDeliveyInfo(){
	var shopNo=$("#shopNo").val();
	var subOrderId =$("#subOrderId").val();
	if(shopNo==''){
		alert("店铺编码不能为空");
		return;
	}
	if(subOrderId==''){
		alert("配送单号不能为空");
		return;
	}
	 var data={};
	$.ajax({
        type:"post",
		url:"<%=basePath %>orderToDrag/queryDeliveyInfo.action",
		data:{  
	    	  "shopNo" : shopNo,
	    	  "subOrderId" :subOrderId
	      }, 
		cache:false,
		success:function(data){
			if(typeof(data)!="undefined" && "" != data && null != data){
				if("noGomeTracking"==data){
					alert("该订单号不是国美代运的订单");
				}else if("noOrder"==data){
					alert("订单不存在");
				}else{
					var dataObj = eval("("+data+")");
					var lcCorpList = dataObj.lcCorpList;
					var logistics = $("#logistics");
					if(lcCorpList.length>0){
						logistics.next().html('');
						for(var i=0;i<lcCorpList.length;i++){
							var html1='<li id='+lcCorpList[i].logisticsId+'abcba'+decodeURI(lcCorpList[i].logisticsName)+'abcba'+lcCorpList[i].logisticsFlag+'><a href="##" title="啦1" >'+lcCorpList[i].logisticsName+'</a></li>';
							logistics.next().append(html1);
						}
					}
					var addressList = dataObj.addressList;
					var address=$("#trackingAddressId");
					if(addressList.length>0){
						address.next().html('');
						for(var i=0;i<addressList.length;i++){
							var html2='<li id='+addressList[i].id+'><a href="##" title="啦1">'+decodeURI(addressList[i].address_short_name)+'</a></li>';
							address.next().append(html2);
						}
					}
					$("#mobile").val(dataObj.mobile);
					$("#delievInfo").show();
					$("#send").show();
				}
			}
		   },
		   error:function(){
			  alert("服务器正忙，请稍候再试");
		   }
	});
}

function hiddenDiv(){
	$("#delievInfo").hide();
	$("#send").hide();
}

/**
 * 推送报文信息
 */
function send(){
	var shopNo=$("#shopNo").val();
	var subOrderId =$("#subOrderId").val();
	var orderNum=$("#orderNum").val();
	var trackingNo =$("#trackingNo").val();
	var mobile=$("#mobile").val();
	var trackingAddressId = $("#trackingAddressId").attr("tag");
	var logistics = $("#logistics").attr("tag");
	if(shopNo==''){
		alert("店铺编码不能为空");
		return;
	}
	if(subOrderId==''){
		alert("配送单号不能为空");
		return;
	}
	if(logistics==''){
		alert("请选择物流公司");
		return;
	}
	
	if(orderNum==''){
		alert("包裹件数不为空");
		return;
	}else{
		if(orderNum==0){
  			alert("你的包裹件数不能为0！");
  			return;
  		}
	}
	if(trackingNo==''){
		alert("运单号不能为空");
		return;
	}
	if(trackingNo.split(',').length!=orderNum){
		alert("您的包裹件数与运单数量不一致，请修改后重新提交！");
		return;
	}
	if(trackingAddressId==''){
		alert("请选择发货地");
		return;
	}
	if(mobile==''){
		alert("手机号不能为空");
		return;
	}else{
		 if(!(/^1(3|4|5|7|8)\d{9}$/.test(mobile))){ 
		       alert("手机号码不符合规则");  
		       return; 
		   } 
	}
		$.ajax({
	        type:"post",
			url:"<%=basePath %>orderToDrag/sendOrderXml.action",
			data:{  
		    	  "shopNo" : shopNo,
		    	  "subOrderId" :subOrderId,
		    	  "orderNum":orderNum,
		    	  "trackingNo":trackingNo,
		    	  "trackingAddressId":trackingAddressId,
		    	  "logistics":logistics,
		    	  "mobile":mobile
		      }, 
			cache:false,
			//dataType:"json",
			success:function(data){
					if(typeof(data)!="undefined" && "" != data && null != data){
						alert("推成成功！");
					}else{
						//登录按钮置灰、获取短信验证码按钮置灰
						alert("失败");
						return;
					}
			   },
			   error:function(){
				  alert("服务器正忙，请稍候再试");
				  return;
			   }
		});
}

//清空
function clear(){
	$("#sendForm input[type='text']").attr("value","");
	$("#sendForm b").text("全部");
	$("#sendForm b").removeAttr("style"); 
	$("#orderId").val("");
	$("#trackingAddressId").attr("tag","");
	$("#trackingAddressId").val("请选择");
	$("#logistics").attr("tag","");
	$("#logistics").val("请选择");
}


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