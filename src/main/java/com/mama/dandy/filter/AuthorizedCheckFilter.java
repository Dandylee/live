package com.mama.dandy.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mama.dandy.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mama.dandy.common.Resjson;

@Component("authorizedCheckFilter")
public class AuthorizedCheckFilter implements HandlerInterceptor{
	private static final Logger logger = LoggerFactory
			.getLogger(AuthorizedCheckFilter.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		if(session == null){
			logger.error("session is null?? ");
			throw new BusinessException("-1","请先登录");
		}
		boolean isLogged =false;
		try{
			logger.info("isLog:{}",session.getAttribute("isLogged"));
			Object obj = session.getAttribute("isLogged");
			if(obj == null){
				isLogged = true;
			}else{
				isLogged = (boolean) obj;
			}

		}catch (Exception e){
			e.printStackTrace();
			logger.error("get session attribute has error :",e);
			throw new BusinessException("-1","请先登录");
		}
		if(isLogged){
			return true;
		}else{
			String requestWith = request.getHeader("x-requested-with");
			if ("XMLHttpRequest".equals(requestWith)) { //ajax请求
				response.setHeader("sessionTimeOut", "1");
				logger.info("用户尚未登录");
				Resjson result = new Resjson();
				result.setCode("100");
				result.setMsg("用户尚未登录");
				response.getWriter().write(result.toJsonString());
			} else {//其他请求
				logger.info("用户尚未登录");
				response.sendRedirect("/index.html");
			}
			return false;
		}
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
