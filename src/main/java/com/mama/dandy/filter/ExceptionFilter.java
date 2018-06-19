package com.mama.dandy.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import com.mama.dandy.common.Resjson;
import com.mama.dandy.exception.BusinessException;
import com.mama.dandy.utils.JsonUtils;

public class ExceptionFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try{
			chain.doFilter(request, response);
		}catch(Exception ex){
			Resjson resBase = new Resjson();
        	String json = "";
			if (ex instanceof BusinessException) {
				BusinessException cause = (BusinessException) ex;
				resBase.setCode(cause.getCode());
				resBase.setMsg(cause.getMsg());
			} else if(ex.getCause() instanceof BusinessException){
				BusinessException cause = (BusinessException) ex.getCause();
				resBase.setCode(cause.getCode());
				resBase.setMsg(cause.getMsg());
			}
			else {
				resBase.setCode("-1");
				resBase.setMsg(ex.getMessage());
//				HttpServletResponse res = (HttpServletResponse)response;
//				HttpServletRequest req = (HttpServletRequest)request;
//				res.sendRedirect(req.getContextPath() + "/error.jsp");
//				logger.error(ex.getMessage(), ex);
//				return;
			}
			json = JsonUtils.toString(resBase);
			response.setContentType("application/json");
			response.getWriter().print(json);
			
		}
		// TODO Auto-generated method stub

	}

}
