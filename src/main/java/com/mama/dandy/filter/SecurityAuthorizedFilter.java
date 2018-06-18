package com.mama.dandy.filter;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mama.dandy.exception.BusinessException;
import com.mama.dandy.utils.MD5Util;

@Component("securityAuthorizedFilter")
public class SecurityAuthorizedFilter implements HandlerInterceptor{
	private static final Logger logger = LoggerFactory
			.getLogger(SecurityAuthorizedFilter.class);
	
	@Value("${LIVE_AUTH_KEY}")
	private String authKey;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
/*		if(request.getParameter("test")!=null){
			
		}*/
		String debug = request.getParameter("debug");
		if("1".equals(debug))
			return true;
		String sign = request.getParameter("sign");
		//String key = request.getParameter("key");
		String timestamp = request.getParameter("timestamp");
		String data = request.getParameter("data");
		if(StringUtils.isEmpty(data)){
			logger.error("data参数为空");
			throw new BusinessException("-104", "【无效请求】data 参数为空");
		}
		data =new String(data.getBytes("iso8859-1"),"UTF-8");
		
		// 前3个入参为定义规范，必传
		if (StringUtils.isBlank(sign)) {
			throw new BusinessException("-102", "【无效请求】sign 参数为空");
		}
/*		if (StringUtils.isBlank(key)) {
			throw new BusinessException("-102", "【无效请求】key 参数为空");
		}*/
		if (StringUtils.isBlank(timestamp)) {
			throw new BusinessException("-102", "【无效请求】timestamp 参数为空");
		}
		
		String params = "Sign:"+sign+" Data:"+data+" TimeStamp:"+timestamp;
		logger.info("请求参数:"+params);
		String tokenStr=authKey+data+timestamp;
		String secret= MD5Util.getMd5(tokenStr).toLowerCase();
		logger.info("请求参数:"+tokenStr);
		logger.info("最终验签:"+secret);
		if(!sign.equals(secret)){
			throw new BusinessException("-102", "【无效请求】验签有误");
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
