package com.mama.dandy.domain;

import java.sql.Timestamp;

public class Machine {
	
	private Integer id;
	
	private Integer userId;
	
	private String machineName;
	
	private String machineCode; //机器型号

	//private Integer totalJobSum; //机器作业的总次数
	
	//private Timestamp lastOperTime; //机器最后作业时间 


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	
	
}
