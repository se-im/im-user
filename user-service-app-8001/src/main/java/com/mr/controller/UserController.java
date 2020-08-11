package com.mr.controller;



import com.mr.common.RequestContext;
import com.mr.entity.vo.UserVo;
import com.mr.exception.BusinessErrorEnum;
import com.mr.response.ServerResponse;
import com.mr.response.error.BusinessException;

import com.mr.entity.po.User;
import com.mr.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/user/")
@Api(tags = "用户相关的api")
public class UserController {



    @Autowired
    private IUserService iUserService;

    @ApiOperation(value = "登录" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true,dataType = "String",example = "cui"),
            @ApiImplicitParam(name = "password", value = "密码",required = true,dataType = "String",example = "123456"),
    })
    @PostMapping(value = "/login")
    public ServerResponse<String> login(String username, String password) throws BusinessException {

        Integer namespace = RequestContext.getNamespace();
        if(namespace == null){
            throw new BusinessException("namespace 为空或不合法！");
        }
        String token = iUserService.login(username, password, namespace);
        return ServerResponse.success(token);
    }




    @PostMapping(value = "/register")
    @ApiOperation("注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true,dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码",required = true,dataType = "String"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = false),
            @ApiImplicitParam(name = "phone", value = "电话", required = false)
    })
    public ServerResponse<User> register(User user) throws BusinessException {
        Integer namespace = RequestContext.getNamespace();
        if(namespace == null){
            throw new BusinessException("namespace 为空或不合法！");
        }
        user.setNamespace(namespace);
        iUserService.register(user);
        return ServerResponse.success();
    }

    @ApiOperation("获取当前用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token信息", required = true,dataType = "String"),
    })
    @PostMapping(value = "/detail/token")
    public ServerResponse<UserVo> getUserInfo(String token) throws BusinessException {

        if(token == null){
            throw new BusinessException("token 不能为空！");
        }
        UserVo userVo = iUserService.getUserByToken(token);
        return ServerResponse.success(userVo);
    }
//
//
//
//

    @ApiOperation(value = "根据id查询用户信息" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true,dataType = "Long"),
    })
    @GetMapping("/detail/id/{id}")
    public ServerResponse<UserVo> queryById(@PathVariable Long id) throws BusinessException {
        Integer namespace = RequestContext.getNamespace();
        if(namespace == null){
            throw new BusinessException("namespace 为空或不合法！");
        }
        UserVo userVo = iUserService.getUserById(id,namespace);
        return ServerResponse.success(userVo);
    }



    @PostMapping("/reset_password1")
    @ResponseBody
    public ServerResponse<String> resetPassword(String passwordOld,String passwordNew) throws BusinessException {
        UserVo userVo = iUserService.getUserByToken(RequestContext.getToken());
        if(userVo == null){
            return ServerResponse.error("用户未登录");
        }
        iUserService.resetPassword(passwordOld, passwordNew, userVo);
        return ServerResponse.success();
    }
//
    @PostMapping(value = "/update")
    @ResponseBody
    public ServerResponse<UserVo> update_information(UserVo userVoNew) throws BusinessException {
        UserVo userVo = iUserService.getUserByToken(RequestContext.getToken());
        if(userVo == null){
            return ServerResponse.error("用户未登录");
        }
        iUserService.updateUserInfo(userVoNew);
        return ServerResponse.success();
    }


}