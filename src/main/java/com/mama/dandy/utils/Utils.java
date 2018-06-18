package com.mama.dandy.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Utils {
	public static void removeCookie(HttpServletRequest request,HttpServletResponse response, String name) {
		Cookie c = new Cookie(name, "");
		c.setPath(request.getContextPath());
	    c.setMaxAge(0);
		response.addCookie(c);
	}

}
