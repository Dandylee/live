package com.mama.dandy.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mama.dandy.bo.ListAccountBo;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.condition.ParamsRequestCondition;

import com.mama.dandy.bo.AddAccountBo;
import com.mama.dandy.bo.AddUserBo;
import com.mama.dandy.domain.LoginAccount;
import com.mama.dandy.domain.User;

@Repository
public class UserDao extends BaseDao<User> {

	public int uptUser(AddUserBo bo){
			String sql ="INSERT INTO shuoma_user (account,password,userName,email,mobile) VALUES (?,?,?,?,?)";
			List<Object> params= new ArrayList<Object>();
			params.add(bo.getAccount());
			params.add(bo.getPassword());
			params.add(bo.getUserName());
			params.add(bo.getEmail());
			params.add(bo.getMobile());
			
			int involveCount=this.getJdbcTemplate().update(sql, params.toArray());
			return involveCount;
		}
	
	public User getUser(String account){
		String sql = "SELECT * FROM shuoma_user where account=?";
		return this.getObject(sql, User.class, account);
	}
	
	public int delUser(AddUserBo bo){
		String sql = "DELETE FROM shuoma_user WHERE ACCOUNT=?";
		return this.getJdbcTemplate().update(sql,bo.getAccount());
	}
	
	
	public int modifyUser(AddUserBo bo){
		String sql = "UPDATE shuoma_user SET account='"+bo.getAccount()+"'";
		List<Object> params= new ArrayList<Object>();
		if(bo.getPassword()!=null){
			sql+=" ,password=?";
			params.add(bo.getPassword());
		}
		if(bo.getMobile()!=null){
			sql+=" ,mobile=?";
			params.add(bo.getMobile());
		}
		if(bo.getEmail()!=null){
			sql+=",email=?";
			params.add(bo.getEmail());
		}
		sql+=" where account='"+bo.getAccount()+"'";
		return this.getJdbcTemplate().update(sql, params.toArray());
	}

	public List<User> listUser(ListAccountBo bo){
		String sql = "SELECT * FROM shuoma_user a where 1=1";
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
		return findList(sql, User.class, params.toArray());
	}


	public int countUserRecord(ListAccountBo bo){
		String sql = "SELECT count(1) FROM shuoma_user a where 1=1";
		List<Object> params= new ArrayList<Object>();
		if(bo.getUserName()!=null){
			sql +=" AND a.userName like '%"+bo.getUserName()+"%'";
		}
		return getJdbcTemplate().queryForObject(sql, params.toArray(),Integer.class);
	}
	
}
