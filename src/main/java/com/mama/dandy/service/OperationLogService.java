package com.mama.dandy.service;

import java.util.List;

import com.mama.dandy.bo.AddLogBo;
import com.mama.dandy.bo.ListPrintLogBo;
import com.mama.dandy.domain.OperationLog;

public interface OperationLogService {

	public int addOperationLog(AddLogBo bo);

	public int uptOperationLog();

	public int delOperationLog();

	public List<OperationLog> listOperationLog(ListPrintLogBo bo);
	
	public int count(ListPrintLogBo bo);
}
