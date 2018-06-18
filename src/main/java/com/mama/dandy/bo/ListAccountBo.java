package com.mama.dandy.bo;

import com.mama.dandy.common.page;

public class ListAccountBo extends page{
	
	private String userName;
	
	private String account;
	
	private Integer level;
	
	private Integer addTime;
	
	private String psw;


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
