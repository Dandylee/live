package com.mama.dandy.service;

import java.util.List;

import com.mama.dandy.domain.Machine;

public interface MachineService {

	public void addMachine();
	
	public void uptMachine();
	
	public void delMachine();
	
	public List<Machine> listAllMachine();
}
