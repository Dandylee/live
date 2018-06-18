package com.mama.dandy.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mama.dandy.bo.*;
import com.mama.dandy.domain.VerifyCode;
import com.mama.dandy.service.VerifyCodeService;
import com.mama.dandy.vo.VerifyResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mama.dandy.common.Resjson;
import com.mama.dandy.common.SystemConstant;
import com.mama.dandy.domain.OperationLog;
import com.mama.dandy.domain.User;
import com.mama.dandy.exception.BusinessException;
import com.mama.dandy.service.ApkService;
import com.mama.dandy.service.OperationLogService;
import com.mama.dandy.service.UserService;
import com.mama.dandy.utils.JsonUtils;

@Controller
@RequestMapping(value ="/action/external")
public class ExternalController {
	private static final Logger logger = LoggerFactory.getLogger(ExternalController.class);
	
	@Autowired
	OperationLogService operationService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ApkService apkService;

	@Autowired
	VerifyCodeService verifyCodeService;
	
	@ResponseBody
	@RequestMapping("/addUser")
	public String addUser(String data){
		data = characterEncode(data);
		Resjson json = new Resjson();
		AddUserBo bo;
		try{
			bo = JsonUtils.toBean(data, AddUserBo.class);
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException("140", "参数有误");
		}
		int count = userService.addUser(bo);
		if(count<1){
			json.setCode("170");
			json.setMsg("添加用户失败");
		}
		return JsonUtils.toJSONString(json);
	}
	
	
	@ResponseBody
	@RequestMapping("/delUser")
	public String delUser(String data){
		data = characterEncode(data);
		Resjson json = new Resjson();
		AddUserBo bo;
		try{
			bo = JsonUtils.toBean(data, AddUserBo.class);
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException("140", "参数有误");
		}
		int count = userService.delUser(bo);
		if(count<1){
			json.setCode("177");
			json.setMsg("删除用户失败");
		}
		return JsonUtils.toJSONString(json);
	}
	
	@RequestMapping("/addLog")
	@ResponseBody
	public String addLog(String data){
		data = characterEncode(data);
		Resjson json = new Resjson();
		AddLogBo bo;
		try{
			bo = JsonUtils.toBean(data, AddLogBo.class);
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException("140", "参数有误");
		}
		int count = operationService.addOperationLog(bo);
		if(count<1){
			json.setCode("141");
			json.setMsg("插入日志失败");
		}
		
		return JsonUtils.toJSONString(json);
	}
	
	@RequestMapping("/modifyUser")
	@ResponseBody
	public String modifyUser(String data){
		data = characterEncode(data);
		Resjson json = new Resjson();
		AddUserBo bo;
		try{
			bo = JsonUtils.toBean(data, AddUserBo.class);
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException("140", "参数有误");
		}
		int count = userService.uptUser(bo);
		if(count<1){
			json.setCode("180");
			json.setMsg("修改用户失败");
		}
		return JsonUtils.toJSONString(json);
	}

	@RequestMapping("/verify")
	@ResponseBody
	public String verifyCode(String data){
		data = characterEncode(data);
		Resjson json = new Resjson();
		VerifyCodeBo bo;
		try{
			bo = JsonUtils.toBean(data, VerifyCodeBo.class);
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException("140", "参数有误");
		}
		VerifyResultVo verifyCode = verifyCodeService.checkVerifyCode(bo);
		json.setData(verifyCode);
		return JsonUtils.toJSONString(json);
	}

	private String characterEncode(String data) {
		try {
			data=new String(data.getBytes("iso8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.info("编码有误");
			throw new BusinessException("142", "编码有误,请转成utf-8编码");
		}
		return data;
	}

	@RequestMapping("/checkLogin")
	@ResponseBody
	public String checkLogin(String data){
		data = characterEncode(data);
		Resjson json = new Resjson();
		UserBo bo;
		try{
			bo = JsonUtils.toBean(data, UserBo.class);
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException("140", "参数有误");
		}
		User user = userService.checkUser(bo);
		if(user!=null){
			json.setData(user);
		}
		return JsonUtils.toJSONString(json);
	}
	
	@RequestMapping("/isLatestVersion")
	@ResponseBody
	public String String(String data){
		data = characterEncode(data);
		Resjson json = new Resjson();
		UpdateApkBo bo;
		try{
			bo = JsonUtils.toBean(data, UpdateApkBo.class);
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException("140", "参数有误");
		}
		String fileName =  apkService.updateApk(bo.getAppName());
		if(fileName==null){
			json.setCode("321");
			json.setMsg("目前已经是最新版本了");
		}
		JSONObject result = new JSONObject();
		result.put("version", fileName.substring(fileName.lastIndexOf(SystemConstant.APK_NAME)));
		json.setData(result);
		return JsonUtils.toJSONString(json);
	}
	
	@RequestMapping("/updateApk")
	@ResponseBody
	public void  updateApk(String data,HttpServletRequest request,HttpServletResponse response) {
		data = characterEncode(data);
		Resjson json = new Resjson();
		String fileName = apkService.updateApk(JsonUtils.toJSON(data).getString("appName"));
		if (fileName == null) {
			logger.info("已经是最新版本");
			return;
		}
		String downLoadFileName = fileName.substring(fileName.lastIndexOf(SystemConstant.APK_NAME));
		response.setContentType(request.getServletContext().getMimeType(fileName));
		response.setHeader("Content-Disposition", "attachment;filename=" + downLoadFileName);
		try {
			InputStream in = new FileInputStream(fileName);
			OutputStream out = response.getOutputStream();
			//写文件
			int b;
			while ((b = in.read()) != -1)
				out.write(b);

			in.close();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			json.setCode("333");
			json.setMsg("下载apk失败");
			logger.error("下载apk失败");
			e.printStackTrace();
		} catch (IOException e) {
			json.setCode("333");
			json.setMsg("下载apk失败");
			logger.error("下载apk失败");
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String args[]){
		AddLogBo bo =new AddLogBo();
		bo.setMachineId(15);
		bo.setUserId(null);
		bo.setOperInfo("打印条形码1050 次");
		bo.setOperType(2);
		bo.setMachineName("终结者二号");
		bo.setUserName("是谁");
		bo.setOperTime(new Date());
		System.out.print(JsonUtils.toJSONString(bo));
		String data ="{\"machineId\":15,\"machineName\":\"终结者二号\",\"operInfo\":\"打印条形码1050 次\",\"operTime\":\"2016-09-22 20:50:14\",\"operType\":2,\"userName\":\"是谁\"}";
		AddLogBo obj = JsonUtils.toBean(data, AddLogBo.class);
		System.out.print(obj.getUserName());
	}
}
