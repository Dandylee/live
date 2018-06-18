package com.mama.dandy.bo;

import com.mama.dandy.utils.MD5Util;

public class AddUserBo {
	private String userName;
	
	private String password;
	
	private Integer id;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	private String email;
	
	private String account;
	
	private String mobile;
	
	public static void main(String args[]){
		String data="{\"userName\":\"dandy\",\"account\":\"dandy\",\"password\":\"dandy\"}";
		String key=MD5Util.getMd5("shuoma");
	    String finalStr=key+data+100;
	    System.out.println(finalStr);
		System.out.println(MD5Util.getMd5(finalStr).toLowerCase());
	}

}
