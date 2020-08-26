package com.im.user.controller;



import com.im.user.entity.enums.GenderEnum;
import com.im.user.entity.po.User;
import com.im.user.entity.vo.UserRegisterVo;
import com.im.user.entity.vo.UserVo;
import com.im.user.exception.BusinessErrorEnum;
import com.im.user.service.IUserService;
import com.mr.common.RequestContext;
import com.mr.response.ServerResponse;
import com.mr.response.error.BusinessException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
public class UserController {

    @Autowired
    private IUserService iUserService;

    @ApiOperation(value = "登录" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true,dataType = "string"),
            @ApiImplicitParam(name = "password", value = "密码",required = true,dataType = "string"),
    })
    @PostMapping(value = "/login")
    public ServerResponse<String> login(String username, String password) throws BusinessException {

        String token = iUserService.login(username, password);
        return ServerResponse.success(token);
    }


    @PostMapping(value = "/register")
    @ApiOperation("注册")
    public ServerResponse<User> register(@Valid @ModelAttribute UserRegisterVo userRegisterVo) throws BusinessException {
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

    @ApiOperation("根据token获取当前用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token信息", required = true,dataType = "String"),
    })
    @GetMapping(value = "/detail/token")
    public ServerResponse<UserVo> getUserInfo(String token) throws BusinessException {

        if(token == null){
            throw new BusinessException("token 不能为空！");
        }
        UserVo userVo = iUserService.getUserByToken(token);
        return ServerResponse.success(userVo);
    }


    @ApiOperation(value = "根据id查询用户信息" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true,dataType = "Long"),
    })
    @GetMapping("/detail/id/{id}")
    public ServerResponse<UserVo> queryById(@PathVariable Long id) throws BusinessException {
        UserVo userVo = iUserService.getUserById(id);
        return ServerResponse.success(userVo);
    }

    @ApiOperation(value = "重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "passwordOld", value = "用户原密码", required = true,dataType = "String"),
            @ApiImplicitParam(name = "passwordNew", value = "用户新密码", required = true,dataType = "String"),
    })
    @PostMapping(value = "/reset_password")
    public ServerResponse<String> resetPassword(String passwordOld,String passwordNew) throws BusinessException {
        UserVo userVo = iUserService.getUserByToken(RequestContext.getToken());
        if(userVo == null){
            return ServerResponse.error("用户未登录");
        }
        iUserService.resetPassword(passwordOld, passwordNew, userVo);
        return ServerResponse.success();
    }

    //TODO UserVo 和 User之间的转换已完成 8/24 8：00
    @ApiOperation(value = "更新用户信息")
    @PostMapping(value = "/update")
    public ServerResponse<String> update_information(UserVo userVoNew) throws BusinessException {
        UserVo userVo = iUserService.getUserByToken(RequestContext.getToken());
        if(userVo == null){
            return ServerResponse.error("用户未登录");
        }
        User user = new User();
        userVoNew.setId(userVo.getId());
        BeanUtils.copyProperties(userVoNew, user);
        String gender = userVoNew.getGender();
        user.setGender(GenderEnum.nameOf(gender).getCode());
        Optional.ofNullable(userVoNew.getBirthday()).ifPresent(birthday -> user.setBirthday(new Date(birthday)));
        iUserService.updateUserInfo(user);
        return ServerResponse.success();
    }

    @ApiOperation(value = "注销用户")
    @PostMapping(value = "/delete")
    public ServerResponse<String>  LogOutUser() throws BusinessException{
        UserVo userVo = iUserService.getUserByToken(RequestContext.getToken());
        if(userVo == null){
            return ServerResponse.error("用户未登录");
        }
        iUserService.deleteUser(userVo);
        return ServerResponse.success();
    }





    @RequestMapping(value = "/unlogin")
    public ServerResponse<String> unlogin() throws BusinessException
    {
        throw new BusinessException(BusinessErrorEnum.NEED_LOGIN);

    }
}