package com.mama.dandy.service.impl;

import com.mama.dandy.bo.VerifyCodeBo;
import com.mama.dandy.dao.VerifyDao;
import com.mama.dandy.domain.VerifyCode;
import com.mama.dandy.service.VerifyCodeService;
import com.mama.dandy.utils.JsonUtils;
import com.mama.dandy.utils.RandomStringUtils;
import com.mama.dandy.vo.CommonVo;
import com.mama.dandy.vo.VerifyResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {

    private static final Logger logger = LoggerFactory.getLogger(VerifyCodeServiceImpl.class);

    @Autowired
    private VerifyDao verifyDao;

    @Override
    public List<VerifyCode> generateVerifyCode(VerifyCodeBo bo) {
        List<VerifyCode> list = new ArrayList<>();
        List<String> strList = new ArrayList<>();
        VerifyCode verifyCode = buildVerifyCode(bo);
        while(strList.size()<bo.getNum()){
            if(!strList.contains(verifyCode.getVerifyCode())){
                list.add(verifyCode);
                strList.add(verifyCode.getVerifyCode());
            }
            verifyCode = buildVerifyCode(bo);
        }
        //判断是否已经插入了,如果是则重新生成
        List<String> exists = verifyDao.findByCodes(strList);
        if(!CollectionUtils.isEmpty(exists)){
            logger.info("已插入的记录:"+ JsonUtils.toJSONString(exists));
            //从list仲改remove已经插入的记录
            removeFromList(exists,list);
            logger.info(" after remove record nums :{}",list.size());

            for(String vCode : exists){
                VerifyCode code = buildVerifyCode(bo);
                logger.info("newly generate code:{}",code.getVerifyCode());
                //判断重新生产的code是否已经存在
                int times=0;
                while(verifyDao.findByCode(code.getVerifyCode())!=null){
                    times++;
                    logger.info("第{}次重试,旧code{},新code{}",times,vCode,code.getVerifyCode());
                    //最多是三次
                    if(times>3){
                        code =null;
                        break;
                    }
                    code = buildVerifyCode(bo);
                }
                if (code == null) continue;
                list.add(code);
            }
        }

        verifyDao.batchSave(list);
        logger.info("finally insert record nums :{}",list.size());
        return list;
    }

    private void removeFromList(List<String> codes,List<VerifyCode> list){
        Iterator<VerifyCode> iter = list.iterator();
        while (iter.hasNext()) {
            VerifyCode item = iter.next();
            if (codes.contains(item.getVerifyCode())) {
                logger.info("remove {}",item.getVerifyCode());
                iter.remove();
            }
        }
    }

    public static void main(String args[]){
        List<String> codes = new ArrayList<>();
        codes.add("b");
        codes.add("v");
        System.out.println(codes.contains("b"));
        System.out.println(codes.contains("B"));
    }

    @Override
    public VerifyResultVo checkVerifyCode(VerifyCodeBo bo) {
        VerifyResultVo vo = new VerifyResultVo();
        VerifyCode verifyCode = verifyDao.findByCode(bo.getVerifyCode());
        if (verifyCode!=null){
            BeanUtils.copyProperties(verifyCode,vo);
            if(verifyCode.getIsValid()==1){
                vo.setVerifyStatus(400);
                vo.setResult("校验码已验证");
            }else{
                verifyCode.setIsValid(1);
                verifyCode.setVerifyTime(System.currentTimeMillis()/1000);
                vo.setVerifyStatus(200);
                vo.setResult("校验码验证成功");
                verifyDao.updateStatus(verifyCode);
            }
        }else{
            vo.setVerifyStatus(300);
            vo.setResult("无效的验证码");
        }
        return vo;
    }

    @Override
    public CommonVo pageQuery(VerifyCodeBo bo) {
        CommonVo vo = new CommonVo();
        int count = 0;
        try {
            count = verifyDao.countCodes(bo);
            if(count<=0){
                vo.setRows(new ArrayList());
                vo.setTotal(count);
                return vo;
            }
            List<VerifyCode> list = verifyDao.listCodes(bo);
            vo.setTotal(count);
            vo.setRows(list);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("list codes error",e);
            vo.setRows(new ArrayList<VerifyCode>());
        }

        return vo;
    }

    private VerifyCode buildVerifyCode(VerifyCodeBo bo) {
        VerifyCode code = new VerifyCode();
        code.setIsValid(0);
        code.setAgentCode(bo.getAgentCode());
        code.setCreateTime(System.currentTimeMillis()/1000);
        code.setOperator(bo.getOperator());
        String verifyCode = RandomStringUtils.generateStr(12);
        code.setVerifyCode(verifyCode);
        return code;
    }
}
