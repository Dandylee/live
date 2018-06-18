package com.mama.dandy.service;

import java.util.List;

import com.mama.dandy.bo.AddAccountBo;
import com.mama.dandy.bo.AddUserBo;
import com.mama.dandy.bo.ListAccountBo;
import com.mama.dandy.bo.LoginBo;
import com.mama.dandy.bo.UserBo;
import com.mama.dandy.domain.LoginAccount;
import com.mama.dandy.domain.User;
import com.mama.dandy.vo.CommonVo;

public interface UserService {
	
	public User checkUser(UserBo bo);
	
	public LoginAccount checkLogin(LoginBo bo);

	CommonVo listAllUser(ListAccountBo bo);

    public int uptUser(AddUserBo bo);
	
	public int delUser(AddUserBo bo);
	
	public int addUser(AddUserBo bo);
	
	public int addLoginAccount(AddAccountBo bo);
	
	public int uptLoginAccount(AddAccountBo bo);
	
	public int delLoginAccount(AddAccountBo bo);
	
	public List<LoginAccount> listLoginAccount(ListAccountBo bo)throws Exception;
	
	public int count(ListAccountBo bo)throws Exception;
	
	public LoginAccount queryLoginAccount(String account);

}
