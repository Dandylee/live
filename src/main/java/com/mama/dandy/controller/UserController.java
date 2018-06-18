package com.mama.dandy.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mama.dandy.common.ResponseCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mama.dandy.bo.AddAccountBo;
import com.mama.dandy.bo.ListAccountBo;
import com.mama.dandy.bo.LoginBo;
import com.mama.dandy.common.Resjson;
import com.mama.dandy.domain.LoginAccount;
import com.mama.dandy.service.UserService;
import com.mama.dandy.utils.JsonUtils;
import com.mama.dandy.utils.MD5Util;
import com.mama.dandy.utils.PropertiesUtils;
import com.mama.dandy.utils.Utils;
import com.mama.dandy.vo.CommonVo;

@Controller
@RequestMapping(value ="/action/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private static String userName="Dandy";
	
	private static String COOKIE_SESSION_ID_NAME="CSID";
	
	private static String passWord="goubilei";
	
	@Autowired
	UserService userService;
	
	private static Map<String,String> logMap=new HashMap<String,String>();
	
	static{
		try {
			String value=PropertiesUtils.readProperties("redis.properties").getProperty("UNAUTH_KEY");
			logMap.put("196EF306120D44BCAD6EC1D2DAFCC7F0", value);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@ResponseBody
	@RequestMapping(value="/login")
	public String login(HttpServletRequest request,HttpServletResponse response,LoginBo bo){
		
		if(bo.getAccount()==null){
			request.getSession().setAttribute("checked", "userName not right");
			return JsonUtils.toJSONString(new ResponseCode(-1,"userName not right"));
		}
		
		if(bo.getPassWord()==null){
			request.getSession().setAttribute("checked", "password not right");
			return JsonUtils.toJSONString(new ResponseCode(-1,"password not right"));
		}
		
		LoginAccount account = userService.checkLogin(bo);
		if(account!=null){
			request.getSession().setAttribute("account",account);
			request.getSession().setAttribute("level",account.getLevel());
		}else{
			request.getSession().setAttribute("checked","用户帐号或密码错误");
			return JsonUtils.toJSONString(new ResponseCode(-1,"用户帐号或密码错误"));
		}
		
		//String userAccountMD5 = MD5Util.getMd5(bo.getUserName() + new Date().getTime() + new Random().nextDouble());
		String userAccountMD5 = MD5Util.getMd5(bo.getUserName() + "123");
		Cookie cookieSession = new Cookie("USESSIONID",userAccountMD5);
		cookieSession.setPath(request.getContextPath());
		cookieSession.setMaxAge(7 * 24 * 60 * 60);
		response.addCookie(cookieSession);
		
		logger.info("用户"+account.getUserName()+"登录成功");
		request.getSession().setAttribute("name", account.getUserName());
		request.getSession(true).setAttribute("isLogged", true);
		request.getSession().setAttribute(userAccountMD5, true);
		return JsonUtils.toJSONString(ResponseCode.success);
	}
	
	
	/**
	 * 判断用户有没有登录，如果登录了返回true
	 */
	@ResponseBody
	@RequestMapping("/checkLogin")
	public String checkLogin(HttpServletRequest request){
		boolean result = false;
		String userAccountMD5 = null;
		
		// 判断cookie是否存在
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if("USESSIONID".equals(cookies[i].getName())){
					userAccountMD5 = cookies[i].getValue();
					if (StringUtils.isNotBlank(userAccountMD5)) {
						boolean isLogged =true;
						if (isLogged) {
							result = true;
							request.getSession(true).setAttribute("isLogged", true);
							request.setAttribute("isLogged", true);
							logger.info("用户已经登录过可以免登录");
						}
					}
					break;
				}
			}
		}
		return "{\"code\":\"100\",\"msg\":\"ok\",\"data\":{\"result\":" + result + "}}";
	}
	
	@ResponseBody
	@RequestMapping("/validateCaptcha")
	public String validate(HttpServletRequest request){
		boolean re = validateCaptcha(request);
		return re ? "{\"data\":\"ok\"}" : "{\"data\":\"error\"}";
	}
	
	
    private boolean validateCaptcha(HttpServletRequest request){
    	//request captcha
	    String captcha = request.getParameter("captcha");
	    if (StringUtils.isBlank(captcha)) {
			return false;
		}
	    
	    //cookied csid
    	String csid = getFromCookie(request, COOKIE_SESSION_ID_NAME);
    	if (StringUtils.isBlank(csid)) {
			return false;
		}
    	
    	//redis key
    	String key = csid + CaptchaImageCreateAction.KAPTCHA_SESSION_KEY;
	    String exspectCaptcha = (String) request.getSession().getAttribute(key);

	    return StringUtils.equalsIgnoreCase(exspectCaptcha, captcha);
	}
	
    public static String getCsidFromCookie(HttpServletRequest request,HttpServletResponse response){
		String csid = getFromCookie(request, COOKIE_SESSION_ID_NAME);
		
		if (StringUtils.isBlank(csid)){
			csid = UUID.randomUUID().toString().replace("-", "");
			Cookie cookie = new Cookie(COOKIE_SESSION_ID_NAME,csid);
			cookie.setPath(request.getContextPath());
			cookie.setMaxAge(30 * 60);
			response.addCookie(cookie);
		}
		return csid;
	}
	
	public static String getFromCookie(HttpServletRequest request, String keyName){
		String val = "";
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if(StringUtils.equals(keyName, cookies[i].getName())){
					val = cookies[i].getValue();
					break;
				}
			}
		}
		return val;
	}
	
	
	@RequestMapping("/logout")
	public String logOut(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//Utils.removeCookie(request,response, "CSID");
		Utils.removeCookie(request,response, "USESSIONID");
		// 遍历cookies
/*		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if("USESSIONID".equals(cookies[i].getName())){
					String userAccountMD5 = cookies[i].getValue();
					Utils.removeCookie(response, "USESSIONID");
					//jedisUtil.set("User_" + userAccountMD5, "", 0);// 有效时间设为0秒，即删除了
					break;
				}
			}
		}*/
		//response.sendRedirect("../../index.html");
		return "redirect:../../index.html";
	}

	@RequestMapping("/listUser")
	@ResponseBody
	public String listAllUser(HttpServletRequest request, HttpServletResponse response){
		return null;
		
	}

	@RequestMapping("/listAppUser")
	@ResponseBody
	public String listAllUser(ListAccountBo bo){
		CommonVo vo = userService.listAllUser(bo);
		return JsonUtils.toJSONString(vo);

	}
	
	@RequestMapping("/listLoginAccount")
	@ResponseBody
	public String listLoginAccount(ListAccountBo bo){
		
		CommonVo vo = new CommonVo();
		List<LoginAccount> resList = new ArrayList<>();
		int count=0;
		try {
			resList = userService.listLoginAccount(bo);
			count = userService.count(bo);
		} catch (Exception e) {
			e.printStackTrace();
			count=0;
			resList=new ArrayList<LoginAccount>();
			logger.error("查询用户列表失败");
		}
		vo.setRows(resList);
		vo.setTotal(count);
		return JsonUtils.toJSONString(vo);
	}
	
	@RequestMapping("/createLoginAccount")
	@ResponseBody
	public String createLoginAccount(HttpServletRequest request,AddAccountBo bo){
		Resjson result = new Resjson();
		int count= userService.addLoginAccount(bo);
		if(count<1){
			result.setCode("101");
			result.setMsg("添加用户失败");
			return JsonUtils.toJSONString(result);
		}
		return result.toJsonString();
	}
	
	@RequestMapping("/uptLoginAccount")
	@ResponseBody
	public String uptLoginAccount(HttpServletRequest request,AddAccountBo bo){
		Resjson result = new Resjson();
		int count= userService.uptLoginAccount(bo);
		if(count<1){
			result.setCode("102");
			result.setMsg("更新用户失败");
			return JsonUtils.toJSONString(result);
		}
		return result.toJsonString();
	}
	
	@RequestMapping("/delLoginAccount")
	@ResponseBody
	public String delLoginAccount(HttpServletRequest request,AddAccountBo bo){
		Resjson result = new Resjson();
		int count= userService.delLoginAccount(bo);
		if(count<1){
			result.setCode("103");
			result.setMsg("删除用户失败");
			return JsonUtils.toJSONString(result);
		}
		return result.toJsonString();
	}
	
	@RequestMapping("/queryLoginAccount")
	@ResponseBody
	public String queryLoginAccount(HttpServletRequest request,String account){
		Resjson result = new Resjson();
		LoginAccount loginAccount = userService.queryLoginAccount(account);
		if(loginAccount!=null)
		result.setData(loginAccount);
		return JsonUtils.toJSONString(loginAccount);
	}
}
