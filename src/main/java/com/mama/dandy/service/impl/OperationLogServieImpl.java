package com.mama.dandy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mama.dandy.bo.AddLogBo;
import com.mama.dandy.bo.ListPrintLogBo;
import com.mama.dandy.dao.OperationLogDao;
import com.mama.dandy.domain.OperationLog;
import com.mama.dandy.exception.BusinessException;
import com.mama.dandy.service.OperationLogService;

@Service
public class OperationLogServieImpl implements OperationLogService {
	
	@Autowired
	OperationLogDao operationDao;
	
	@Override
	public int addOperationLog(AddLogBo bo) {
		// TODO Auto-generated method stub
		if(bo.getOperTime()==null){
			throw new BusinessException("120","操作时间不能为空");
		}
		if(bo.getOperInfo()==null){
			throw new BusinessException("121","操作信息不能为空");
		}
		if(bo.getOperType()==null){
			throw new BusinessException("122","操作渠道不能为空");
		}
		return operationDao.insertOperationLog(bo);
	}

	@Override
	public int uptOperationLog() {
		return 0;
		// TODO Auto-generated method stub

	}

	@Override
	public int delOperationLog() {
		return 0;
		// TODO Auto-generated method stub

	}

	@Override
	public List<OperationLog> listOperationLog(ListPrintLogBo bo) {
		// TODO Auto-generated method stub
		return operationDao.listOperationLog(bo);
	}

	@Override
	public int count(ListPrintLogBo bo) {
		// TODO Auto-generated method stub
		return operationDao.count(bo);
	}

}
