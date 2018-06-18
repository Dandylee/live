package com.mama.dandy.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mama.dandy.bo.ListPrintLogBo;
import com.mama.dandy.domain.OperationLog;
import com.mama.dandy.service.OperationLogService;
import com.mama.dandy.utils.JsonUtils;
import com.mama.dandy.vo.CommonVo;

@Controller
@RequestMapping(value ="/action/operate")
public class OperateController {
	
	private static final Logger logger = LoggerFactory.getLogger(OperateController.class);

	@Autowired
	OperationLogService operateService;
	
	
	@ResponseBody
	@RequestMapping("/listOperationLog")
	public String listOperationLog(ListPrintLogBo bo){
		CommonVo vo = new CommonVo();
		List<OperationLog> list = operateService.listOperationLog(bo);
		int count = operateService.count(bo);
		if(list.isEmpty()){
			list = new ArrayList<OperationLog>();
			count=0;
		}
		vo.setRows(list);
		vo.setTotal(count);
		return JsonUtils.toJSONString(vo);
	}
}
