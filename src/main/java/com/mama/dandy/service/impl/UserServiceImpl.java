package com.mama.dandy.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.mama.dandy.vo.CommonVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mama.dandy.bo.AddAccountBo;
import com.mama.dandy.bo.AddUserBo;
import com.mama.dandy.bo.ListAccountBo;
import com.mama.dandy.bo.LoginBo;
import com.mama.dandy.bo.UserBo;
import com.mama.dandy.dao.LoginAccountDao;
import com.mama.dandy.dao.UserDao;
import com.mama.dandy.domain.LoginAccount;
import com.mama.dandy.domain.User;
import com.mama.dandy.exception.BusinessException;
import com.mama.dandy.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	LoginAccountDao loginAccountDao;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


	@Autowired
	UserDao userDao;
	
	@Override
	public User checkUser(UserBo bo) {
		// TODO Auto-generated method stub
		User user;
		if(bo.getAccount()==null){
			throw new BusinessException("195", "用户帐号为空");
		}
		if(bo.getPassword()==null){
			throw new BusinessException("196", "用户密码为空");
		}
		try{
			 user =userDao.getUser(bo.getAccount());
			if(!user.getPassword().equals(bo.getPassword())){
				throw new BusinessException("197","用户密码有误");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException("198","该用户不存在");
			
		}
		return user;
	}

	@Override
	public CommonVo listAllUser(ListAccountBo bo) {
		CommonVo vo = new CommonVo();
		List<User> resList = new ArrayList<>();
		int count=0;
		try {
			resList = userDao.listUser(bo);
			count = userDao.countUserRecord(bo);
		} catch (Exception e) {
			e.printStackTrace();
			count=0;
			resList=new ArrayList<User>();
		}
		vo.setRows(resList);
		vo.setTotal(count);
		return vo;
	}

	@Override
	public int uptUser(AddUserBo bo) {
		if(bo.getAccount()==null){
			throw new BusinessException("181","用户帐号不能为空");
		}
		int count = userDao.modifyUser(bo);
		return count;
		// TODO Auto-generated method stub

	}

	@Override
	public int delUser(AddUserBo bo) {
		// TODO Auto-generated method stub
		int result=0;
		if(bo.getAccount()==null){
			throw new BusinessException("171", "用户帐号不能为空");
		}
		result = userDao.delUser(bo);
		return result;
	}

	@Override
	public int addUser(AddUserBo bo) {
		// TODO Auto-generated method stub
		int result=0;
		if(bo.getAccount()==null){
			throw new BusinessException("171", "用户帐号不能为空");
		}
		if(bo.getPassword()==null){
			throw new BusinessException("172","用户密码不能为空");
		}
		if(bo.getUserName()==null){
			throw new BusinessException("173","用户名不能为空");
		}
		try{
			result=userDao.uptUser(bo);
		}catch(Exception e){
			throw new BusinessException("173","用户名帐号已经存在");
		}
		return result;
	}
	
	@Override
	public int addLoginAccount(AddAccountBo bo) {
		// TODO Auto-generated method stub
		Integer id=bo.getId();
			if(id==null){
				return loginAccountDao.createLoginAccount(bo);
			}else{
				throw new BusinessException("101", "该用户已经存在");
			}
	}

	@Override
	public List<LoginAccount> listLoginAccount(ListAccountBo bo,Integer level) {
		// TODO Auto-generated method stub
		return loginAccountDao.listAllLoginAccount(bo,level);
	}
	
	public int count(ListAccountBo bo){
		return loginAccountDao.countRecord(bo);
	}

	@Override
	public int uptLoginAccount(AddAccountBo bo,Integer level) {
		// TODO Auto-generated method stub
		Integer id=bo.getId();
		if(id==null){
			throw new BusinessException("103", "用户id不能为空");
		}
		Integer userLevel = null;
		try{
			userLevel = loginAccountDao.getAccountIdByAccount(id);
			logger.info("operator update level is {},userLevel is {}",level,userLevel);
		}catch(Exception e){
			logger.error("update account error",e);
			throw new BusinessException("102", "用户不存在");
		}
		if(level!=null && userLevel!=null && level>userLevel){
			throw new BusinessException("112", "不能编辑权限比你高的账户");
		}
		if(level!=null && bo.getLevel()<level){
			throw new BusinessException("113", "用户权限只能小于当前用户的权限");
		}
		return loginAccountDao.uptLoginAccount(bo);
	}

	@Override
	public int delLoginAccount(AddAccountBo bo,Integer level) {
		// TODO Auto-generated method stub
		Integer id=bo.getId();
		if(id==null){
			throw new BusinessException("103", "用户id不能为空");
		}
		Integer userLevel = null;
		try{
			userLevel = loginAccountDao.getAccountIdByAccount(id);
			logger.info("operator delete level is {},userLevel is {}",level,userLevel);
		}catch(Exception e){
			logger.error("delete account error",e);
			throw new BusinessException("102", "用户不存在");
		}
		if(level!=null && userLevel!=null && level>userLevel){
			throw new BusinessException("112", "不能删除权限比你高的账户");
		}
		return loginAccountDao.delLoginAccount(bo);
	}
	
	public LoginAccount queryLoginAccount(String accountName){
		LoginAccount account=null;
		try{
			account = loginAccountDao.getLoginAccount(accountName);
		}catch(Exception e){
			throw new BusinessException("102", "用户不存在");
		}
		return account;
	}

	@Override
	public LoginAccount checkLogin(LoginBo bo) {
		// TODO Auto-generated method stub
		if(bo.getAccount()==null){
			throw new BusinessException("161","登录名不能为空");
		}
		if(bo.getPassWord()==null){
			throw new BusinessException("162","密码名不能为空");
		}
		try{
			LoginAccount account=loginAccountDao.getLoginAccount(bo.getAccount());
			if(account.getPwd().equals(bo.getPassWord())){
				return account;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException("163","该用户不存在");
		}
		return null;
	}
}
