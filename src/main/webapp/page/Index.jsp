<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
       <form action="user/login" method="post" id="login_form">
	                <div>
	                    <input  id="userId" type="text" name="username" placeholder="请输入用户名"  />
	                </div>
	                <div >
	                    <input  id="pwdId" type="password" name="passwd" placeholder="请输入密码"  />
	                </div>
	                <div>
	                	<input  id="captchaId" type="text" name="captcha" placeholder="验证码" maxlength="4" />
	                	<img id="captchaMsgId" class="capPicMsg"/>
	                	<img id="captchaImageId" src="captcha-image.html"/>
	                </div>
	                
	                <div>
	                    <input id="remember-me" type="checkbox" name="remember_me" checked="checked"/>
	                    <label for="remember-me">记住密码，下次自动登录</label>
	                </div>
	                <input type="submit" value="商家登录"  />
                </form>
</body>
</html>