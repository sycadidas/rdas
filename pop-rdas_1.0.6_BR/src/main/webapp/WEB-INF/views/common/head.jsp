<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"  isELIgnored="false"%> 
<div class="header">
		<div class="headerIN">
			<h1><a href="##">国美在线.NPOP数据修复系统</a></h1>
			<a href="##" onclick="logonOut()" class="logonOut">退出</a>
		</div>
</div>
<script type="text/javascript">
function logonOut(){
			 $.ajax({  
				     url:"<%=basePath %>check/logonOut.action",  
				     type:'post',
				     cache:false,  
				     success:function (data) {
				    	 if(data==='true'){
				    		 window.location.replace("<%=basePath %>hello.jsp");
				    	}
				    }
				 }); 
	}
</script>