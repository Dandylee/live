package com.mama.dandy.controller;


import com.mama.dandy.bo.VerifyCodeBo;
import com.mama.dandy.common.Resjson;
import com.mama.dandy.domain.VerifyCode;
import com.mama.dandy.exception.BusinessException;
import com.mama.dandy.service.VerifyCodeService;
import com.mama.dandy.utils.JsonUtils;
import com.mama.dandy.vo.CommonVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value ="/action/verify")
public class VerifyCodeController{

    private static final Logger logger = LoggerFactory.getLogger(VerifyCodeController.class);

    @Autowired
    private VerifyCodeService service;

    @RequestMapping("/save")
    @ResponseBody
    public String save(VerifyCodeBo bo,HttpServletRequest request){
        if(StringUtils.isEmpty(bo.getAgentCode())){
            throw new BusinessException("-1","代理商编号不能胃为空");
        }
        if(bo.getNum()==null || bo.getNum()>500){
            throw new BusinessException("-1","参数有误");
        }
        Resjson resjson = new Resjson();
        resjson.setData(service.generateVerifyCode(bo));

        bo.setOperator( request.getSession()==null?"":(String)(request.getSession().getAttribute("account")));
        return JsonUtils.toJSONString(resjson);

    }

    @RequestMapping("/listCodes")
    @ResponseBody
    public String save(VerifyCodeBo bo){
        CommonVo vo = service.pageQuery(bo);

        return JsonUtils.toJSONString(vo);

    }
}
