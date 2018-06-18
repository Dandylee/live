<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<style type="text/css">a {padding:15px;}</style>
	<link rel="stylesheet" href="/live/jquery-easyui-1.4.3/themes/default/easyui.css"></link>
	<link rel="stylesheet" href="/live/jquery-easyui-1.4.3/themes/icon.css"></link>
	<link rel="stylesheet" href="/live/jquery-easyui-1.4.3/demo/demo.css"></link>
	<link rel="stylesheet" href="/live/css/page-all.css"></link>
	<script type="text/javascript" src="/live/jquery-easyui-1.4.3/jquery.min.js"></script>
	<script type="text/javascript" src="/live/jquery-easyui-1.4.3/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="/live/jquery-easyui-1.4.3/easyui-lang-zh_CN.js"></script>
	
	<script type="text/javascript" src="/live/js/function.js"></script>
	<script>
	$(function() {
		$('.tabLink').click(function(){
			var url = $(this).attr('url');
			var title = $(this).attr('title');
			decoration.tabLinkInit(url,title);
		})
	})
	</script>
<title>首页</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" style="height:110px;padding:10px;overflow:hidden;">
		<div style="float:left">
		<img src="<%=basePath%>img/shuomamedium.png" align="center"/>
		</div>
		<div style="float:right">
	 	<font style="color:black;">欢迎您！</font>
		<font id="welcomeMsg" style="color:black">
		<c:if test="${not empty sessionScope.name}">
		${sessionScope.name}
		</c:if>
		</font>
		<a href="<%=basePath%>action/user/logout">注销登陆</a>
		</div>
	</div>
	
	<div id="panel" class="easyui-panel" title="导航菜单"
    style="width:13%;padding:10px;background:#fafafa;"
    data-options="region:'west',iconCls:'icon-save',
    collapsible:true">
    <div class="tabLink" style="height:30px" url="<%=basePath%>page/homePage.jsp" title="首页">首页</div>
    <div class="tabLink" url="<%=basePath%>page/user-list.jsp" title="用户管理">用户管理</div>
	<div class="tabLink" url="<%=basePath%>page/code-generate.jsp" title="生成验证码">生成验证码</div>
	<div class="tabLink" url="<%=basePath%>page/app-user.jsp" title="App用户">App用户</div>
    <div class="tabLink" url="<%=basePath%>page/userOperationLog.jsp" title="查看日志">查看日志</div>
	</div>

	<div id="tt" class="easyui-tabs" data-options="region:'center'">
		<div title="首页" style="margin-top: 100px">
			<center>
			<label style="font-size: 60px;font-family:serif;color: red;">欢迎使用ShuoMa在线平台</label>
			<dir style="margin-top: 10px"/>
			<label style="font-size: 20px;font-family:serif;color: red">推荐使用<a href="http://www.google.cn/chrome/browser/desktop/index.html" style="font-size: 20px;">Chrome浏览器</a></label>
			<dir style="margin-top: 10px"/>
			<label style="font-size: 30px;font-family:serif;color: red">版权所有<a href="http://www.somrt.cc" style="font-size: 30px;" target="_blank">www.somrt.cc</a>侵权必究</label>
			</center>
		</div>
	</div>
</body>
</html>