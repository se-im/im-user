package com.zmz.service.impl;

import com.zmz.common.Const;
import com.zmz.common.JwtToken;
import com.zmz.common.RedisPrefixConst;
import com.zmz.common.ResponseCode;
import com.zmz.constant.TokenHashConst;
import com.zmz.exception.BusinessErrorEnum;
import com.zmz.mapper.UserMapper;
import com.zmz.response.error.BusinessException;
import com.zmz.response.error.CommonErrorAdapter;
import com.zmz.user.entity.po.User;
import com.zmz.user.service.IUserService;
import com.zmz.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;




@Component
@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Value("${token.expiration}")
    private int expiration;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 用户登陆
     * @param username
     * @param password
     * @return
     * @throws BusinessException
     */
    @Override
    public String login(String username, String password) throws BusinessException {
        int resultCount = userMapper.checkUsername(username);
        if(resultCount == 0 ){
            log.warn("A invalid user {} tried to login", username);
            throw new BusinessException(BusinessErrorEnum.USER_NOT_EXIST);
        }

        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user  = userMapper.selectLogin(username,md5Password);
        if(user == null){
            throw new BusinessException(BusinessErrorEnum.INVALID_PASSWORD);
        }
        log.info("User Id: {} Name {} logined", user.getId(), user.getUsername());
        user.setPassword(StringUtils.EMPTY);
        String json = JsonUtil.obj2String(user);
        String token = null;
        try {
            token = JwtToken.createToken();
        } catch (Exception e) {
            e.printStackTrace();
        }

        redisTemplate.opsForHash().put(RedisPrefixConst.TOKEN_PREFIX+token, TokenHashConst.USER, user);
        redisTemplate.expire(RedisPrefixConst.TOKEN_PREFIX+token, expiration, TimeUnit.MINUTES);

        return token;
    }

    /**
     * 注册用户
     * @param user
     * @return
     * @throws BusinessException
     */

    @Override
    public User register(User user) throws BusinessException {
        this.checkValid(user.getUsername(), Const.USERNAME);

        this.checkValid(user.getEmail(),Const.EMAIL);

        user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if(resultCount == 0){
            throw new BusinessException(BusinessErrorEnum.REGISTER_FAILED);
        }
        return user;
    }


    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    @Override
    public User getUserByToken(String token)
    {
        log.info("getUserByToken被调用");
        User user = (User)redisTemplate.opsForHash().get(RedisPrefixConst.TOKEN_PREFIX + token, TokenHashConst.USER);
        if(user == null)
        {
            return null;
        }
        redisTemplate.expire(RedisPrefixConst.TOKEN_PREFIX+token, expiration, TimeUnit.MINUTES);
        return user;
    }


    /**
     * 用户注册参数校验
     * @param str
     * @param type
     * @throws BusinessException
     */
    private void checkValid(String str,String type) throws BusinessException {
        if(StringUtils.isNotBlank(type)){
            //开始校验
            if(Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUsername(str);
                if(resultCount > 0 ){
                    throw new BusinessException(new CommonErrorAdapter(5000, "用户名已存在"));
                }
            }
            if(Const.EMAIL.equals(type)){
                int resultCount = userMapper.checkEmail(str);
                if(resultCount > 0 ){
                    throw new BusinessException(new CommonErrorAdapter(5000, "email已存在"));
                }
            }
        }else{
            throw new BusinessException(new CommonErrorAdapter(5000, "参数错误"));

        }

    }

    /**
     * 获取二级密保问题
     */
    @Override
    public String selectQuestion(String username) throws BusinessException {

        try {
            this.checkValid(username,Const.USERNAME);
        } catch (BusinessException e) {
            throw new BusinessException(BusinessErrorEnum.USER_NOT_EXIST);
        }

        String question = userMapper.selectQuestionByUsername(username);
        if(StringUtils.isNotBlank(question)){
            return question;
        }
        throw new BusinessException(new CommonErrorAdapter(ResponseCode.ERROR.getCode(), "找回密码的问题是空的"));
    }

    /**
     * 重制密码
     * @param passwordOld
     * @param passwordNew
     * @param user
     * @return
     */
    @Override
    public void resetPassword(String passwordOld,String passwordNew,User user) throws BusinessException {
        //防止横向越权,要校验一下这个用户的旧密码,一定要指定是这个用户.因为我们会查询一个count(1),如果不指定id,那么结果就是true啦count>0;
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());
        if(resultCount == 0){
            throw new BusinessException(new CommonErrorAdapter(ResponseCode.ERROR.getCode(), "旧密码错误"));
        }

        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if(updateCount <= 0){
            throw new BusinessException(new CommonErrorAdapter(ResponseCode.ERROR.getCode(), "密码更新失败"));
        }

    }


    /**
     * 更新用户信息
     * @param user
     * @return
     * @throws BusinessException
     */
    @Override
    public User updateInformation(User user) throws BusinessException {
        //username是不能被更新的
        //email也要进行一个校验,校验新的email是不是已经存在,并且存在的email如果相同的话,不能是我们当前的这个用户的.
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(),user.getId());
        if(resultCount > 0){
            throw new BusinessException(new CommonErrorAdapter(ResponseCode.ERROR.getCode(), "email已存在,请更换email再尝试更新"));
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());

        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if(updateCount <= 0){
            throw new BusinessException(new CommonErrorAdapter(ResponseCode.ERROR.getCode(), "用户信息更新失败"));
        }
        return updateUser;
    }



    @Override
    public User selectUserById(Integer userId) throws BusinessException {
        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null){
            throw new BusinessException(BusinessErrorEnum.USER_NOT_EXIST);
        }
        user.setPassword(StringUtils.EMPTY);
        user.setAnswer(StringUtils.EMPTY);
        return user;

    }


}
