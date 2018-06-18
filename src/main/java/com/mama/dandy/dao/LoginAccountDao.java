package com.mama.dandy.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mama.dandy.bo.AddAccountBo;
import com.mama.dandy.bo.ListAccountBo;
import com.mama.dandy.domain.LoginAccount;

@Repository
public class LoginAccountDao extends BaseDao<LoginAccount> {

	public int uptLoginAccount(AddAccountBo  bo){
		//Integer primaryId = getAccountIdByAccount(bo.getId());
		String sql = "UPDATE shuoma_login_account SET account=?,userName=?,pwd=?,level=? where id= ?";
		List<Object> params= new ArrayList<Object>();
		params.add(bo.getAccount());
		params.add(bo.getUserName());
		params.add(bo.getPsw());
		params.add(bo.getLevel());
		params.add(bo.getId());
		
		return this.getJdbcTemplate().update(sql, params.toArray());
	}
	
	public int delLoginAccount(AddAccountBo bo){
		//Integer primaryId = getAccountIdByAccount(bo.getId());
		String sql = "DELETE FROM shuoma_login_account WHERE id =?";
		return this.getJdbcTemplate().update(sql,bo.getId());
	}
	
	public Integer getAccountIdByAccount(Integer id){
		String sql = "SELECT id FROM shuoma_login_account where id=?";
		return this.getJdbcTemplate().queryForObject(sql, Integer.class, id);
	}
	
	
	public int createLoginAccount(AddAccountBo bo){
		String sql ="INSERT INTO shuoma_login_account (account,userName,pwd,level,lastLogin) VALUES (?,?,?,?,?)";
		List<Object> params= new ArrayList<Object>();
		params.add(bo.getAccount());
		params.add(bo.getUserName());
		params.add(bo.getPsw());
		params.add(bo.getLevel());
		params.add(new Date().getTime()/1000);
		
		int involveCount=this.getJdbcTemplate().update(sql, params.toArray());
		return involveCount;
	}
	
	public List<LoginAccount> listAllLoginAccount(ListAccountBo bo){
		String sql = "SELECT * FROM shuoma_login_account a where 1=1";
		List<Object> params= new ArrayList<Object>();
		if(bo.getUserName()!=null){
			sql +=" AND a.userName like '%"+bo.getUserName()+"%'";
		}
		sql +=" ORDER BY a.id asc";
		if(bo.getRows()!=null && bo.getPage()!=null){
			sql +=" LIMIT ?,?";
			params.add((bo.getPage()-1)*bo.getRows());
			params.add(bo.getRows());
		}
		return findList(sql, LoginAccount.class, params.toArray());
	}
	
	public LoginAccount getLoginAccount(String account){
		String sql = "SELECT * FROM shuoma_login_account where account=?";
		return this.getObject(sql, LoginAccount.class, account);
	}
	
	public int countRecord(ListAccountBo bo){
		String sql = "SELECT count(1) FROM shuoma_login_account a where 1=1";
		List<Object> params= new ArrayList<Object>();
		if(bo.getUserName()!=null){
			sql +=" AND a.userName like '%"+bo.getUserName()+"%'";
		}
		return getJdbcTemplate().queryForObject(sql, params.toArray(),Integer.class);
	}
}
