/**
 * 
 */

Date.prototype.format = function (format) {
    var date = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1
                ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
        }
    }
    return format;
};

var ajaxLoading = function() {
	$("<div class=\"datagrid-mask\"></div>").css({
		display : "block",
		width : "100%",
		height : $(window).height()
	}).appendTo("body");
	$("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo(
			"body").css({
		display : "block",
		left : ($(document.body).outerWidth(true) - 190) / 2,
		top : ($(window).height() - 45) / 2
	});
};

var ajaxLoadEnd = function() {
	$(".datagrid-mask").remove();
	$(".datagrid-mask-msg").remove();
};

/**
 * 整合jquery ajax的页面提交方式
 * 
 * url : 提交路径 string data : 入参 json callback : 回调函数 function
 * 
 * code 返回码 -1 业务异常
 */
var JsonAjaxNew = function(url, data, callback) {
	var param = {};
	param.url = url;
	if (data) {
		param.data = data;
	}
	param.type = 'POST';
	param.dataType = 'json';
	param.success = function(json) {
		ajaxLoadEnd();
		if (json) {
			if (json.code == 100) {
				$.messager.alert('提示', json.msg);
				window.parent.location.href = '/toolbox/index.html';
				return;
			}
		}
		if (callback) {
			callback(json);
		}
	};
	param.error = function(json) {
		ajaxLoadEnd();
		$.messager.alert('提示', '系统错误 ~ 请联系管理员吧☎');
	};

	ajaxLoading();
	$.ajax(param);
};

decoration = {
	tabLinkInit : function(url, title) {
		if ($('#tt').tabs('exists', title)) {
			$('#tt').tabs('select', title);
		} else {
			var content = '<iframe scrolling="auto" frameborder="0"  src="'
					+ url + '" style="width:100%;height:100%;"></iframe>';
			$('#tt').tabs('add', {
				title : title,
				content : content,
				closable : true
			});
		}
	},
	
    parseLong2Date:function(val,row){
      	 if(val==null || val==undefined){
      		 return "";
      	 }
      	 var date =new Date();
      	 date.setTime(val*1000);
      	 var fdate = date.toLocaleString();
      	 return fdate;
       },
       
    formatTime : function(val){
		if(val == '0'){
            return '';
		}
    	return (new Date(val*1000).format('yyyy-MM-dd hh:mm:ss'));
    },

    formatLevel : function(val){
    if(val == 0){
        return 'Root';
    }else if(val==1){
    	return '管理员';
	}else if(val == 2){
    	return '经理';
	}else if(val == 3){
    	return '员工';
	}else{
    	return '测试';
	}
    return (new Date(val*1000).toLocaleString());
},

	isYesOrNo : function (val) {
        if(val==0 || val=="0"){
            return "否"
        }
        if(val==1 || val=="1"){
            return "是"
        }
    },

    addOperate : function (){
      return "<a href='#' onclick='verifyCode.deleteCode()'>删除</a>";
	},

    
    formatOperType : function(val){
    	if(val==1){
    		return 'APP';
    	}else if(val==2){
    		return 'PC';
    	}else{
    		return '终端机';
    	}
    }
},

user = {
		
		newUserDialog : function(){
			operateLevel=$('#operateLevel').val();
			if(operateLevel>1){
				alert('抱歉!只有管理员可以创建新用户');
				return;
			}
		    $('#dlg').dialog('open').dialog('setTitle','新建用户');
		    $('#user_info').form('clear');
/*			$('#form_parent_id').combobox('enable');
		    $('#user_info').form('clear');
		    AjaxNew("/toolbox/action/admin/currentLoginUser",null,function(json){
		    	var user = json.user;
		    	$('#form_parent_id').combobox('setValue',user.userId);
		        $('#form_parent_id').combobox('setText',user.realName);
			});*/
		},
		
		createOrSaveUser : function(){
			var data = {};
			var url;
			var	id=$("#form_user_id").val();
			var	userName=$("#form_user_name").val();
			var password=$("#form_password").val();
			var account=$("#form_account").val();
			var level=$("#form_level").val();
			//var machineNos = $("#machineNos").textbox('getValue');
			
			if(account==""||password==""){
				$.messager.alert('提示','账号和密码不能为空！');
				return false;
			}else if(userName==""){
				$.messager.alert('提示','用户名称不能为空！');
				return false;
			}
			if(!/^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{8,22}$/.test(password) || /^[A-Za-z]{8,}$/.test(password) || /^[0-9]{8,}$/.test(password) ){
				$.messager.alert('提示','密码为8到22位，字母、数字、特殊符号的组合（含八位）');
				return false;
			}
			// if(machineNos){
			// 	machineNos = machineNos.replace(/\n/g,",");
			// 	data.machineNos = machineNos;
			// }
				
			
			if(!level){
				level = 3;//中级的权限
			}
			if(id){
				data.id=id;
			}
			data.userName=userName;
			data.psw = password;
			data.account =account;
			data.level = level;
			
			if(!id){
				url='/live/action/user/createLoginAccount';
			}else{
				url='/live/action/user/uptLoginAccount';
			}
			
			JsonAjaxNew(url,data,function(json){
			    	var code = json.code;
			    	if(code!=0){
			    		alert(json.msg);
			    	}else{
			    		alert('保存成功');
			    		$('#dlg').dialog('close');
			    		$("#usergrid").datagrid("reload");
			    	}
				});
		},
		
		createOrSaveAppUser : function(){
			var data = {};
			var params = {};
			var url;
			var	id=$("#form_user_id").val();
			var	userName=$("#form_user_name").val();
			var password=$("#form_password").val();
			var account=$("#form_account").val();
			var phone=$("#form_phone").val();
			var email=$("#form_email").val();
			var machineNo = $("#form_machineNo").val();
			if(account==""||password==""){
				$.messager.alert('提示','账号和密码不能为空！');
				return false;
			}else if(userName==""){
				$.messager.alert('提示','用户名称不能为空！');
				return false;
			}
			if(!/^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{8,22}$/.test(password) || /^[A-Za-z]{8,}$/.test(password) || /^[0-9]{8,}$/.test(password) ){
				$.messager.alert('提示','密码为8到22位，字母、数字、特殊符号的组合（含八位）');
				return false;
			}
			if(id){
				data.id=id;
			}
			data.userName=userName;
			data.password = password;
			data.account =account;
			data.mobile = phone;
			data.email = email;
			data.machineNo = machineNo;
			params.data = JSON.stringify(data);
			//跳过外部接口的验签
			params.debug = true;
			
			console.log(data);
			console.log(params);
			if(!id){
				url='/live/action/external/addUser';
			}else{
				url='/live/action/external/modifyUser';
			}
			
			JsonAjaxNew(url,params,function(json){
			    	var code = json.code;
			    	if(code!=0){
			    		alert(json.msg);
			    	}else{
			    		alert('保存成功');
			    		$('#dlg').dialog('close');
			    		$("#usergrid").datagrid("reload");
			    	}
				});
		},
		
		editUser : function(){
		    var row = $('#usergrid').datagrid('getSelected');
		    $("#machineNos").textbox('setValue','');
		    if (row){
		        $('#dlg').dialog('open').dialog('setTitle','编辑用户');
		        $('#user_info').form('load',row);
		        // var machineNos = row.machineNos.replace(/,/g,'\n');
		        // $("#machineNos").textbox('setValue',machineNos);
		    }else{ 
		    	$.messager.alert('提示','请选择要编辑的用户！');
		    }
		},
		
		editAppUser : function(){
		    var row = $('#usergrid').datagrid('getSelected');
		    if (row){
		        $('#dlg').dialog('open').dialog('setTitle','编辑用户');
		        $('#user_info').form('load',row);
		    }else{ 
		    	$.messager.alert('提示','请选择要编辑的用户！');
		    }
		},
		
		delUser : function(){
			var data = {};
			var row = $('#usergrid').datagrid('getSelected');
		    if (row){
		    	data.id=row.id;
		    	if(confirm('确定删除')){
		    		JsonAjaxNew("/live/action/user/delLoginAccount",data,function(json){
				    	var code = json.code;
				    	if(code!=0){
				    		alert(json.msg);
				    	}else{
				    		alert('删除成功');
				    		$("#usergrid").datagrid("reload");
				    	}
					});
		    	}
		    }else{ 
		    	$.messager.alert('提示','请选择要删除的用户！');
		    }
		},
		
		delAppUser : function(){
			var data = {};
			var params = {};
			var row = $('#usergrid').datagrid('getSelected');
		    if (row){
		    	data.id=row.id;
				params.data = JSON.stringify(data);
				//跳过外部接口的验签
				params.debug = true;
		    	if(confirm('确定删除')){
		    		JsonAjaxNew("/live/action/external/delUser",params,function(json){
				    	var code = json.code;
				    	if(code!=0){
				    		alert(json.msg);
				    	}else{
				    		alert('删除成功');
				    		$("#usergrid").datagrid("reload");
				    	}
					});
		    	}
		    }else{ 
		    	$.messager.alert('提示','请选择要删除的用户！');
		    }
		},
		
		newAppUserDialog : function(){
		    $('#dlg').dialog('open').dialog('setTitle','新建用户');
		    $('#user_info').form('clear');
		},
		
		retrieve : function(){
			var data={};
			var userName=$('#toolbar_user_name').val();
			if(userName){
				data.userName = userName;
			}
			$("#usergrid").datagrid("load",data);
		},
		
		retrieveUser : function(){
			var data={};
			var userName=$('#toolbar_user_name').val();
			if(userName){
				data.userName = userName;
			}
			var accountId = $('#accountId',window.parent.document).val();
			data.accountId = accountId;
			$("#usergrid").datagrid("load",data);
			return data;
		},
		
		initUserList : function(){
			var data={};
			var userName=$('#toolbar_user_name').val();
			if(userName){
				data.userName = userName;
			}
			var accountId = $('#accountId',window.parent.document).val();
			data.accountId = accountId;
			$("#usergrid").datagrid({
				url:'/live/action/user/listAppUser',
				queryParams:data
			});
		}
},

verifyCode = {
	generateCodeBatch : function(){
		var agentNo = $("#agentNo").val();
        var num = $("#num").numberbox('getValue');
        if(num>500){
            $.messager.alert('提示','一次最多生成500条哦！');
            return;
        }

        var data={};
        data.agentCode = agentNo;
        data.num = num;
        var url = '/live/action/verify/save';
        JsonAjaxNew(url,data,function(json){
            var code = json.code;
            if(code!=0){
                $.messager.alert('提示',json.msg);
            }else{
                $.messager.alert('提示','生成成功,左侧复制！');
                var text = '';
                for(var index in json.data){
                    var item = json.data[index];
                    text = text+item.verifyCode+"\n";
				}
                $('#verifyCodes').textbox('setValue',text);
            }
        });
	},

	deleteCode : function(){
        var data = {};
        var rows = $('#usergrid').datagrid('getSelections');
        if (rows){
        	var ids = '';
        	for(var index in rows){
                ids = ids+rows[index].id+',';
			}
            data.ids=ids;
            if(confirm('确定删除')){
                JsonAjaxNew("/live/action/verify/delete",data,function(json){
                    var code = json.code;
                    if(code!=0){
                        alert(json.msg);
                    }else{
                        alert('删除成功');
                        $("#usergrid").datagrid("reload");
                    }
                });
            }
        }else{
            $.messager.alert('提示','请选择要删除的记录！');
        }
	},

	listCodes : function(){
        var agentCode = $("#agentCode").val();
        var isValid = $("#isValid").val();
        var verifyStartTime = $('#verifyStartTime').datebox('getValue');
        var verifyEndTime = $('#verifyEndTime').datebox('getValue');
        var data={};
        if (verifyStartTime) {
            var date1 =new Date(Date.parse(verifyStartTime.replace(/-/g,"/"))).getTime() / 1000;
            data.startTime = date1;
        }
        if (verifyEndTime) {
            // var endDate=new Date(Date.parse(operTime2.replace(/-/g,"/"))).setHours(23,59,59);
            //var date2 =new Date(endDate) / 1000;
            var date2 =new Date(Date.parse(verifyEndTime.replace(/-/g,"/"))).getTime() / 1000;
            data.endTime = date2;
        }
        data.agentCode = agentCode;
        data.isValid = isValid;

        $("#usergrid").datagrid("load",data);

    }
},


business = {
	retrieve : function() {
		var userName = $("#toolbar_user_name").val();
		var userId = $("#operatorId").val();
		var params = {};
		if (userName != '') {
			params.userName = userName;
		}
		params.userId = userId;
		$("#usergrid").datagrid("load", params);
	},

	queryOperationLog : function() {
		var params = {};
		var userName = $('#user_name').val();
		var operType = $('#oper_Type').val();
		//var operUrl = $('#oper_url').combobox('getValue');
		var operTime1 = $('#oper_time1').datebox('getValue');
		var operTime2 = $('#oper_time2').datebox('getValue');
		if (userName) {
			params.userName = userName;
		}
		if(operType){
			params.operType = operType;
		}
		if (operTime1) {
			 //var date1 =new Date(Date.parse(operTime1.replace(/-/g,"/"))).getTime() / 1000;
			 params.startTime = operTime1;
		}
		if (operTime2) {
			// var endDate=new Date(Date.parse(operTime2.replace(/-/g,"/"))).setHours(23,59,59);
			 //var date2 =new Date(endDate) / 1000; 
			 params.endTime = operTime2;
		}
		$('#tt').datagrid('load', params);
	}

	
}
