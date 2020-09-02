package com.im.user.controller;


import com.im.user.entity.enums.GenderEnum;
import com.im.user.entity.po.CasCode;
import com.im.user.entity.po.User;
import com.im.user.entity.vo.UserRegisterVo;
import com.im.user.service.IUserService;
import com.im.user.service.impl.VerificationCodeService;
import com.mr.response.ServerResponse;
import com.mr.response.error.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/user/")
@Api(tags = "用户相关的api")
@CrossOrigin
public class RegisterController
{

    @Autowired
    private IUserService iUserService;


    @Autowired
    private VerificationCodeService verificationCodeService;



    @GetMapping("/vcode/get")
    @ApiOperation("获取验证码")
    public ServerResponse<CasCode> getVcode()
    {
        CasCode oneRandom = verificationCodeService.getOneRandom();
        return ServerResponse.success(oneRandom);
    }



    @PostMapping(value = "/register")
    @ApiOperation("注册")
    public ServerResponse<User> register(@Valid @ModelAttribute UserRegisterVo userRegisterVo) throws BusinessException
    {

        //url = "http://www.chemicalbook.com/CASDetailList_"+str(i)+".htm"
        boolean b = verificationCodeService.verifyCode(userRegisterVo.getVCodeId(), userRegisterVo.getVCodeInput());
        if(!b){
            throw new BusinessException("验证码不正确");
        }
        User user = new User();
        BeanUtils.copyProperties(userRegisterVo, user);
        GenderEnum genderEnum = GenderEnum.nameOf(userRegisterVo.getGender());
        if(genderEnum != null){
            user.setGender(genderEnum.getCode());
        }
        Optional.ofNullable(userRegisterVo.getBirthday()).ifPresent(birthday -> user.setBirthday(new Date(birthday)));
        iUserService.register(user);
        return ServerResponse.success();
    }
}
