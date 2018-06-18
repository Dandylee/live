package com.mama.dandy.bo;

import java.sql.Timestamp;
import java.util.Date;

import com.mama.dandy.common.page;

public class ListPrintLogBo extends page {

	private String userName;
	
	private Timestamp startTime;
	
	private Timestamp endTime;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Integer getOperType() {
		return operType;
	}

	public void setOperType(Integer operType) {
		this.operType = operType;
	}

	private Integer operType; //1-APP 2-pc 3-OTHER 
}
