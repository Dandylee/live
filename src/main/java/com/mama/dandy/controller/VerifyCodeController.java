package com.mama.dandy.controller;


import com.mama.dandy.bo.VerifyCodeBo;
import com.mama.dandy.common.Resjson;
import com.mama.dandy.common.ResponseCode;
import com.mama.dandy.domain.LoginAccount;
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
import javax.servlet.http.HttpSession;

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
        String code = handle(bo.getAgentCode());
        bo.setAgentCode(code);
        LoginAccount account = checkAuthority(request,2);
        bo.setOperator( account==null?"":account.getUserName());
        Resjson resjson = new Resjson();
        resjson.setData(service.generateVerifyCode(bo));

        return JsonUtils.toJSONString(resjson);

    }

    private LoginAccount checkAuthority(HttpServletRequest request,int level) {
        HttpSession session = request.getSession();
        LoginAccount account = null;
        if(session!=null){
            account = (LoginAccount)session.getAttribute("account");
            if(account!=null && account.getLevel()>level){
                //只有级别为2或者更高等级的才能生产验证码
                throw new BusinessException("-1","当前用户没有权限操作");
            }
        }
        return account;
    }

    private static String handle(String code) {
        int index =0;
        for(char c : code.toCharArray()){
            if(c!='0'){
                break;
            }else{
                index++;
            }
        }
        return  code.substring(index);
    }

    public static void main(String args[]){
        System.out.println(handle("001"));
    }

    @RequestMapping("/listCodes")
    @ResponseBody
    public String save(VerifyCodeBo bo){
        CommonVo vo = service.pageQuery(bo);

        return JsonUtils.toJSONString(vo);

    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(VerifyCodeBo bo,HttpServletRequest request){
        checkAuthority(request,3);
        service.delete(bo);
        return JsonUtils.toJSONString(new Resjson());

    }
}
