package com.mr.controller;



import com.mr.common.RequestContext;
import com.mr.entity.vo.UserRegisterVo;
import com.mr.entity.vo.UserVo;
import com.mr.exception.BusinessErrorEnum;
import com.mr.response.ServerResponse;
import com.mr.response.error.BusinessException;

import com.mr.entity.po.User;
import com.mr.service.IUserFriendService;
import com.mr.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userVo", value = "用户对象", required = true,dataType = "UserRegisterVo"),
    })
    public ServerResponse<User> register(@Valid UserRegisterVo userVo) throws BusinessException {
        User user = new User();
        BeanUtils.copyProperties(userVo, user);
        Optional.ofNullable(userVo.getBirthday()).ifPresent(birthday -> user.setBirthday(new Date(birthday)));
        iUserService.register(user);
        return ServerResponse.success();
    }

    @ApiOperation("获取当前用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token信息", required = true,dataType = "String"),
    })
    @RequestMapping(value = "/detail/token")
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


    @PostMapping(value = "/reset_password")
    public ServerResponse<String> resetPassword(String passwordOld,String passwordNew) throws BusinessException {
        UserVo userVo = iUserService.getUserByToken(RequestContext.getToken());
        if(userVo == null){
            return ServerResponse.error("用户未登录");
        }
        iUserService.resetPassword(passwordOld, passwordNew, userVo);
        return ServerResponse.success();
    }

    @PostMapping(value = "/update")
    public ServerResponse<String> update_information(User userNew) throws BusinessException {
        UserVo userVo = iUserService.getUserByToken(RequestContext.getToken());
        if(userVo == null){
            return ServerResponse.error("用户未登录");
        }
        userNew.setId(userVo.getId());
        iUserService.updateUserInfo(userNew);
        return ServerResponse.success();
    }

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
    public String unlogin(){
        return "您还未登录，请登录后操作！";
    }
}