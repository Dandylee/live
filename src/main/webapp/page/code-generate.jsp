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
    <script type="text/javascript">
        $(function(){
            //user.initUserList();
        });
    </script>
    <style>
        .fitem {margin-top:10px;}
        body{padding: 0px;}
    </style>
</head>
<body>
<center>
    <div style="margin:20px 0 10px 0;"></div>
    <div class="easyui-panel" title="生成校验码" style="width:auto;height:600px;padding:10px;">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'center',split:true" style="width:50%;height:100px;padding:10px;text-align: center">
               <table style="text-align: center;margin-left: 30%;">
                   <tr style="text-align: center"><td>
                       <div style="text-content: center;margin: auto;">
                           <span>代理编号:</span>
                           <input id="agentNo" name="agentNo" class="easyui-textbox" required="true" />
                       </div>
                   </td></tr>
                   <tr style="text-align: center"><td>
                       <div style="margin-top: 20px">
                           <span>生成数量:</span>
                           <input id="num" name="num" class="easyui-numberbox" required="true" />
                       </div>
                   </td></tr>
                   <tr style="height: 10px;"></tr>
                   <tr style="text-align: center;margin-top: 10px;"><td>
                       <a href="javascript:void(0)" id="generateCodeButton" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="verifyCode.generateCodeBatch()">生成密钥</a>
                   </td></tr>
               </table>

            </div>
            <div data-options="region:'east'" style="width:50%;height:100px;padding:10px">
                <input id="verifyCodes" class="easyui-textbox" data-options="multiline:true" value="" style="width:100%;height:95%;padding:0">
            </div>
            <div data-options="region:'south'" style="width:auto;height:400px;padding:10px">
                <table style="width: 100%; height: 548px"
                       id="usergrid" class="easyui-datagrid" url="/live/action/verify/listCodes"
                       toolbar="#toolbar" rownumbers="true" pageSize="15" pageList="[10,15,20,30]" singleSelect="true" pagination="true">
                    <thead>
                    <tr>
                        <th field="id" hidden=true>密钥编号</th>
                        <th field="agentCode" width="18%" align="center">代理商编号</th>
                        <th field="verifyCode" width="20%" align="center">密钥</th>
                        <th field="isValid" width="7%" align="center" formatter="decoration.isYesOrNo">是否认证</th>
                        <th field="verifyTime" width="21%" formatter="decoration.formatTime">认证时间</th>
                        <th field="operator" width="15%" align="center">操作人</th>
                        <th field="createTime" width="20%" align="center" formatter="decoration.formatTime">创建时间</th>
                    </tr>
                    </thead>
                </table>

                <div id="toolbar" style="height:50px;">
                    <center>
                        <div style="margin:12px 0;">
                            <label for="isValid" > 是否验证 </label>
                            <select id="isValid" class="easyui-comobobox" style="width:100px">
                                <option value="">全选</option>
                                <option value="1">是</option>
                                <option value="0">否</option>
                            </select>
                            <label for="agentCode" style="padding:0px 0px 0px 50px">代理商编号：</label>
                            <input id="agentCode" class="easyui-textbox" style="width:120px" >
                            <label style="padding:0px 10px">验证时间：</label>
                            <input id="verifyStartTime" class="easyui-datetimebox" style="width:150px" editable="false">
                            <span style="padding:0px 5px">~</span>
                            <input id="verifyEndTime" class="easyui-datetimebox" style="width:150px" editable="false">
                            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="verifyCode.listCodes()"> 查询</a>
                        </div>
            </div>
        </div>
    </div>
</center>
</body>
</html>