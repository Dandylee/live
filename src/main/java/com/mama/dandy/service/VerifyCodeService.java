package com.mama.dandy.service;

import com.mama.dandy.bo.VerifyCodeBo;
import com.mama.dandy.domain.VerifyCode;
import com.mama.dandy.vo.CommonVo;
import com.mama.dandy.vo.VerifyResultVo;

import java.util.List;

public interface VerifyCodeService {

    List<VerifyCode> generateVerifyCode(VerifyCodeBo bo);

    VerifyResultVo checkVerifyCode(VerifyCodeBo bo);

    CommonVo pageQuery(VerifyCodeBo bo);

    void delete(VerifyCodeBo bo);
}
