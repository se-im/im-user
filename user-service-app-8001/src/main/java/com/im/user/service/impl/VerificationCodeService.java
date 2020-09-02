package com.im.user.service.impl;

import com.im.user.entity.po.CasCode;
import com.im.user.mapper.CasCodeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

@Service
@Slf4j
public class VerificationCodeService
{

    @Resource
    private CasCodeMapper casCodeMapper;

    @Value("${cas.code.debug}")
    private boolean casCodeDebug;

    public CasCode getOneRandom()
    {
        Random random = new Random();
        int i = random.nextInt(1000) + 10;
        CasCode casCode = casCodeMapper.selectByPrimaryKey(i);
        log.info("用户查询验证码 {}", casCode);
        if(!casCodeDebug){
            casCode.setAnwser("");
        }
        return casCode;
    }


    public boolean verifyCode(Integer id, String answerInput)
    {
        CasCode casCode = casCodeMapper.selectByPrimaryKey(id);
        if(casCode == null)
        {
            return false;
        }
        return casCode.getAnwser().equals(answerInput);
    }

}
