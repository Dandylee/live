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
	<title>用户管理</title>
	<link rel="stylesheet" href="<%=basePath%>jquery-easyui-1.4.3/themes/default/easyui.css"></link>
	<link rel="stylesheet" href="<%=basePath%>jquery-easyui-1.4.3/themes/icon.css"></link>
	<link rel="stylesheet" href="<%=basePath%>jquery-easyui-1.4.3/demo/demo.css"></link>
	<script type="text/javascript" src="<%=basePath%>jquery-easyui-1.4.3/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>jquery-easyui-1.4.3/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>jquery-easyui-1.4.3/easyui-lang-zh_CN.js"></script>
	
	<script charset="UTF-8" type="text/javascript" src="<%=basePath%>js/function.js"></script>
</head>
<body>
<center>
		<table style="width: 100%; height: 548px"
		 id="usergrid" class="easyui-datagrid" url="/live/action/user/listLoginAccount"
		  toolbar="#toolbar" rownumbers="true" pageSize="15" pageList="[10,15,20,30]" singleSelect="true" pagination="true">
			<thead>
				<tr>
					<th field="id" hidden=true>用户编号</th>
					<th field="userName" width="18%" align="center">姓名</th>
					<th field="account" width="18%" align="center">账号</th>
					<th field="pwd" width="21%">密码</th>
					<th field="level" width="23%" align="center">权限</th>
					<th field="lastLogin" width="20%" align="center" formatter="decoration.formatTime">最后操作时间</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar" style="height:50px;">
		<center>
		<div style="margin:12px 0;">
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="user.newUserDialog()">新建用户</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="user.editUser()">编辑用户</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="user.delUser()">删除用户</a>
	        <label for="brand_id" style="padding:0px 0px 0px 250px">用户名称：</label>
	        <input id="toolbar_user_name" class="easyui-textbox" style="width:120px" >
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="user.retrieve()"> 查询</a>
    	</div>
    	</center>
    	</div>
    	
    	    <div id="dlg" class="easyui-dialog" style="width:400px;height:250px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
	        <div class="ftitle" >用户信息</div>
	        <form id="user_info" method="post" novalidate>
	        <center>
	            <div class="fitem" width="300px">
	            	<input type="hidden" id="form_user_id" name="id"/> 
	            	<input type="hidden" id="form_login_time" name="loginTime"/> 
	            	<input type="hidden" id="form_login_ip" name="loginIp"/>
	            	<input type="hidden" id="form_create_time" name="createTime"/>
	            	<input type="hidden" id="form_old_level" name="oldLevel"/>
	            	<input type="hidden" id="form_parent_level" name="parentLevel"/>
	                <label>姓&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp名&nbsp&nbsp:&nbsp&nbsp</label>
	                <input id="form_user_name" name="userName" class="easyui-textbox" required="true">
	             </div>
	            <div class="fitem" width="300px">
	                <label>账&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp号&nbsp&nbsp:&nbsp&nbsp</label>
	                <input id="form_account" name="account" class="easyui-textbox" required="true">
	            </div>
	            <div class="fitem" width="300px">
	                <label>密&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp码&nbsp&nbsp:&nbsp&nbsp</label>
	                <input type="password" id="form_password" name="pwd" class="easyui-textbox" data-options="prompt:'8位以上数字字母组合'," required="true">
	            </div>
	            <div class="fitem" width="300px">
	                <label>权&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp限&nbsp&nbsp:&nbsp&nbsp</label>
	                <input id="form_level" name="level" class="easyui-textbox">
	            </div>
<!-- 	            <div class="fitem" width="300px">
	                <label>用&nbsp户&nbsp菜&nbsp单&nbsp&nbsp:&nbsp&nbsp</label>
	                <input id="form_level" name="level" readonly="true" class="easyui-textbox" data-options="prompt:'用户菜单权限'," required="true">
	            </div> -->
			</center>
	        </form>
	    </div>
	    <div id="dlg-buttons">
	        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="user.createOrSaveUser()" style="width:90px">保存</a>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
	    </div>
    	
    	</center>
    	<input type="hidden" id="operateName" name="level"/>
    	<input type="hidden" id="operateLevel" name="level" value="<%=request.getSession().getAttribute("level") %>"/>
</body>
</html>