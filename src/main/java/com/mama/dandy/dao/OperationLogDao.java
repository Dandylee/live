package com.mama.dandy.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mama.dandy.bo.AddAccountBo;
import com.mama.dandy.bo.AddLogBo;
import com.mama.dandy.bo.ListPrintLogBo;
import com.mama.dandy.domain.OperationLog;

@Repository
public class OperationLogDao extends BaseDao<OperationLog>{
	
	public List<OperationLog> listOperationLog(ListPrintLogBo bo){
		StringBuilder sb = new StringBuilder("SELECT * FROM shuoma_machine_print_log a WHERE 1=1");
		List<Object> params = new ArrayList<Object>();
		if(bo.getOperType()!=null){
			sb.append(" AND a.operType = ?");
			params.add(bo.getOperType());
		}
		if(bo.getUserName()!=null){
			sb.append(" AND a.userName like '%"+bo.getUserName()+"%'");
		}
		if(bo.getStartTime()!=null){
			sb.append(" AND a.operTime > ?");
			params.add(bo.getStartTime());
		}
		if(bo.getEndTime()!=null){
			sb.append(" AND a.operTime < ?");
			params.add(bo.getEndTime());
		}
		sb.append(" ORDER BY a.operTime desc ");
		if(bo.getPage()!=null && bo.getRows()!=null){
			sb.append(" LIMIT ?,?");
			params.add((bo.getPage()-1)*bo.getRows());
			params.add(bo.getRows());
		}
		
		return this.findList(sb.toString(), OperationLog.class, params.toArray());
	}
	
	public int count(ListPrintLogBo bo){
		StringBuilder sb = new StringBuilder("SELECT count(1) FROM shuoma_machine_print_log a WHERE 1=1");
		List<Object> params = new ArrayList<Object>();
		if(bo.getOperType()!=null){
			sb.append(" AND a.operType = ?");
			params.add(bo.getOperType());
		}
		if(bo.getUserName()!=null){
			sb.append(" AND a.userName like '%"+bo.getUserName()+"%'");
		}
		if(bo.getStartTime()!=null){
			sb.append(" AND a.operTime > ?");
			params.add(bo.getStartTime());
		}
		if(bo.getEndTime()!=null){
			sb.append(" AND a.operTime < ?");
			params.add(bo.getEndTime());
		}
		return getJdbcTemplate().queryForObject(sb.toString(), params.toArray(),Integer.class);
	}

	
	public int insertOperationLog(AddLogBo bo){
			//Integer primaryId = getAccountIdByAccount(bo.getId());
		String sql ="INSERT INTO shuoma_machine_print_log (userId,machineId,operType,machineName,userName,operInfo,operTime) VALUES (?,?,?,?,?,?,?)";
		List<Object> params= new ArrayList<Object>();
		params.add(bo.getUserId());
		params.add(bo.getMachineId());
		params.add(bo.getOperType());
		params.add(bo.getMachineName());
		params.add(bo.getUserName());
		params.add(bo.getOperInfo());
		params.add(bo.getOperTime());
		
		int involveCount=this.getJdbcTemplate().update(sql, params.toArray());
		return involveCount;
	}
}
