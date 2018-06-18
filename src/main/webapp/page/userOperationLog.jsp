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
	<title>查看日志</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="<%=basePath%>jquery-easyui-1.4.3/themes/default/easyui.css"></link>
	<link rel="stylesheet" href="<%=basePath%>jquery-easyui-1.4.3/themes/icon.css"></link>
	<link rel="stylesheet" href="<%=basePath%>jquery-easyui-1.4.3/demo/demo.css"></link>
	<script type="text/javascript" src="<%=basePath%>jquery-easyui-1.4.3/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>jquery-easyui-1.4.3/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>jquery-easyui-1.4.3/easyui-lang-zh_CN.js"></script>
	
	<script charset="UTF-8" type="text/javascript" src="<%=basePath%>js/function.js"></script>
</head>
<body>
<table id="tt" class="easyui-datagrid" toolbar="#tb" singleSelect="true" rownumbers="true" pageSize="15" pageList="[10,15,20,30]" pagination="true"
		url="/live/action/operate/listOperationLog" fit="true">
		<thead>
			<tr>
				<th field="machineName" width="10%">打印机器</th>
				<th field="userId" width="10%">用户ID</th>
				<th field="userName" width="15%">用户名称</th>
				<th field="operInfo" width="40%">操作行为</th>
				<th field="operType" width="10%" formatter="decoration.formatOperType">操纵来源</th>
				<!-- <th field="respTime" width="10%" >耗时</th>  -->
				<th field="operTime" width="15%">操作时间</th>
			</tr>
		</thead> 
	</table>  
	<div id="tb" style="padding:5px">
		<div style="text-align: center">
			
			<label style="padding:0px 10px">操作用户</label>
  			<input id="user_name" class="easyui-textbox" style="width:120px" >
  			
  			<label style="padding:0px 10px">操作来源</label>
  			<select id="oper_Type" class="easyui-comobobox" style="width:100px">
  			<option value="">操作来源</option>
  			<option value="1">App</option>
  			<option value="2">电脑端</option>
  			<option value="3">终端机</option>
  			</select>
  			
					
         	<label style="padding:0px 10px">操作时间</label>
			<input id="oper_time1" class="easyui-datetimebox" style="width:150px" editable="false">
			<span style="padding:0px 5px">~</span>
			<input id="oper_time2" class="easyui-datetimebox" style="width:150px" editable="false">

			<a href="#" onclick="business.queryOperationLog()" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:70px;margin-left:10px">查询</a>
		</div>
	</div>

</body>
</html>