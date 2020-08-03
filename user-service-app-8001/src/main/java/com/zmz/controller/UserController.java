package com.zmz.controller;



import com.zmz.common.RequestContext;
import com.zmz.exception.BusinessErrorEnum;
import com.zmz.response.ServerResponse;
import com.zmz.response.error.BusinessException;

import com.zmz.user.entity.po.User;
import com.zmz.user.service.IUserService;
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
    private StringRedisTemplate redis;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Autowired
    private IUserService userService;

    @ApiOperation(value = "登录" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true,dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码",required = true,dataType = "String"),
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ServerResponse<String> login(String username, String password) throws BusinessException {
        String token = userService.login(username, password);
        return ServerResponse.success(token);
    }




    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ApiOperation("注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true,dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码",required = true,dataType = "String"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = false),
            @ApiImplicitParam(name = "phone", value = "电话", required = false)
    })
    public ServerResponse<User> register(User user) throws BusinessException {
        User newUser = userService.register(user);
        return ServerResponse.success(newUser);
    }


    @ApiOperation("获取当前用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token信息", required = true,dataType = "String"),
    })
    @RequestMapping(value = "/get_user_info",method = RequestMethod.POST)
    public ServerResponse<User> getUserInfo(String token){

        User user = userService.getUserByToken(token);
        if(user == null)
        {
            return ServerResponse.error("token不合法");
        }else
        {
            return ServerResponse.success(user);
        }
    }




    @ApiOperation(value = "根据id查询用户信息" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true,dataType = "Integer"),
    })
    @GetMapping("/detail/{id}")
    public ServerResponse<User> queryById(@PathVariable Integer id) throws BusinessException {
        User user = userService.selectUserById(id);
        return ServerResponse.success(user);
    }

    @ApiOperation(value = "根据token查询用户信息" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token值", required = true,dataType = "Integer"),
    })
    @PostMapping("/token")
    public ServerResponse<User> queryByToken(String token) throws BusinessException {
        if(token == null)
            throw new BusinessException(BusinessErrorEnum.PARAMETER_EMPTY_ERROR);
        User user = userService.getUserByToken(token);
        return ServerResponse.success(user);
    }

    @RequestMapping(value = "/forget_question",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username) throws BusinessException {
        String s = userService.selectQuestion(username);
        return ServerResponse.success(s);
    }


    @RequestMapping(value = "/reset_password",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPassword(String passwordOld,String passwordNew) throws BusinessException {
        User user = userService.getUserByToken(RequestContext.getToken());
        if(user == null){
            return ServerResponse.error("用户未登录");
        }
        userService.resetPassword(passwordOld, passwordNew, user);
        return ServerResponse.success();
    }

    @RequestMapping(value = "/update/",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> update_information(User user) throws BusinessException {
        userService.updateInformation(user);
        return ServerResponse.success();
    }


}