package live;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mama.dandy.bo.AddAccountBo;
import com.mama.dandy.bo.AddLogBo;
import com.mama.dandy.bo.AddUserBo;
import com.mama.dandy.bo.ListAccountBo;
import com.mama.dandy.bo.ListPrintLogBo;
import com.mama.dandy.dao.LoginAccountDao;
import com.mama.dandy.dao.OperationLogDao;
import com.mama.dandy.dao.UserDao;
import com.mama.dandy.domain.LoginAccount;
import com.mama.dandy.service.UserService;
import com.mama.dandy.utils.JsonUtils;
import com.mama.dandy.utils.MD5Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml",
"classpath:spring-mvc.xml"})
public class LiveTest extends AbstractJUnit4SpringContextTests{

	@Autowired
	LoginAccountDao dao;
	
	@Autowired
	UserService service;
	
	@Autowired
	OperationLogDao operationDao;
	
/*	@Test
	public void testJdbcTemplate(){
		ListAccountBo bo =new ListAccountBo();
		bo.setRows(10);
		bo.setPage(1);;
		List<LoginAccount> list=dao.listAllLoginAccount(bo);
		System.out.println("The result is :"+JsonUtils.toJSONString(list));
		System.out.println(dao.countRecord(bo));
	}*/
	
	@Test
	public void testUpdate(){
/*		AddUserBo bo = new AddUserBo();
		bo.setAccount("dandy");
		bo.setUserName("dandy");
		bo.setPassword("dandy");
		//bo.setLevel(1);
		//bo.setUserName("shuaili111");
		//bo.setPsw("123456");
		int count=service.addUser(bo);
		System.out.println(count);*/
		
	}
	
	@Test
	public void testQuery(){
		AddLogBo bo =new AddLogBo();
		bo.setMachineId(15);
		bo.setUserId(null);
		bo.setOperInfo("打印条形码1050 次");
		bo.setOperType(2);
		bo.setMachineName("终结者二号");
		bo.setUserName("是谁");
		bo.setOperTime(new Date());
/*		String data="{\"userId\":333,machineId=\"444\",\"operInfo\":打印条形码 50 次,\"operType\":1,operTime:\"2016-08-01 17:00:33\"}";
		System.out.println(MD5Util.getMd5(MD5Util.getMd5("shuoma")+data+100).toLowerCase());*/
		System.out.print(JsonUtils.toJSONString(bo));
		//System.out.println(operationDao.insertOperationLog(bo));
		
	}
	
/*	@Test
	public void testQuery(){
		ListPrintLogBo bo =new ListPrintLogBo();
		bo.setRows(10);
		bo.setPage(1);
		System.out.println(JsonUtils.toJSONString(operationDao.listOperationLog(bo)));
		System.out.println(operationDao.count(bo));
	}*/
	
	@Test
	public void testInsert(){
/*		AddAccountBo bo = new AddAccountBo();
		bo.setAccount("476773525");
		bo.setLevel(1);
		bo.setUserName("shuaili");
		bo.setPsw("123456");
		int count=dao.createLoginAccount(bo);
		System.out.println(count);*/
	}
}
