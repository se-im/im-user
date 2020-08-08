package com.mr.service.impl;

import com.mr.common.Const;
import com.mr.common.JwtToken;
import com.mr.common.RedisPrefixConst;
import com.mr.constant.TokenHashConst;
import com.mr.entity.vo.UserVo;
import com.mr.exception.BusinessErrorEnum;
import com.mr.mapper.UserMapper;
import com.mr.response.error.BusinessException;
import com.mr.response.error.CommonErrorAdapter;
import com.mr.entity.po.User;
import com.mr.service.IUserService;
import com.mr.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;




@Component
@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Value("${token.expiration}")
    private int expiration;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 用户登陆
     */
    @Override
    public String login(String username, String password) throws BusinessException {

        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
        {
            throw new BusinessException("用户名或密码不能为空");
        }
        String passwordInDb = userMapper.selectPasswordByUsername(username);
        if(passwordInDb == null)
        {
            log.warn("A user {} which is not exist tried to login", username);
            throw new BusinessException(BusinessErrorEnum.INVALID_USERNAME_OR_PASSWORD);
        }


        String md5Password = MD5Util.MD5EncodeUtf8(password);

        if(!md5Password.equals(passwordInDb))
        {
            log.warn("A user {} with invalid password {} tried to login", username, password);
            throw new BusinessException(BusinessErrorEnum.INVALID_USERNAME_OR_PASSWORD);
        }

        User user = userMapper.selectUserByUsername(username);
        String token = null;
        try {
            token = JwtToken.createToken();
        } catch (Exception e) {
            log.warn(String.valueOf(e.getCause()));
            throw new BusinessException("token创建失败");
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
    public void register(User user) throws BusinessException {

        //1. 判断参数
        if(StringUtils.isEmpty(user.getUsername()))
        {
            throw new BusinessException(BusinessErrorEnum.USERNAME_EMPTY_ERROR);
        }
        if(StringUtils.isEmpty(user.getPassword()))
        {
            throw new BusinessException("密码不能为空！");
        }
        //2. 处理参数
        checkUserParam(user);
        User user1 = userMapper.selectUserByUsername(user.getUsername());
        if(user1 != null){
            throw new BusinessException(BusinessErrorEnum.USER_EXIST);
        }
        //2. 添加
        //3. 判断是否插入成功
        int resultCount = userMapper.insertSelective(user);
        if(resultCount == 0){
            throw new BusinessException(BusinessErrorEnum.REGISTER_FAILED);
        }
    }

    /**
     * 用户注册参数校验
     */
    private void checkUserParam(User user) throws BusinessException {

        if(user.getBirthday() != null) {
            Date date = new Date();
            if (date.before(user.getBirthday())) {
                throw new BusinessException("生日不合法！");
            }
        }
        user.setId(null);
        user.setRole(Const.ROLE.ROLE_CUSTOMER.getCode());
        user.setDeleted(Const.UserStatus.NONDELETED.getCode());
        user.setShown(Const.VISIBILITY.ALLOW.getCode());
        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
    }

    @Override
    public UserVo getUserByToken(String token)
    {
        return null;
    }


    @Override
    public UserVo getUserById(Long userId) throws BusinessException
    {
        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null){
            throw new BusinessException(BusinessErrorEnum.USER_NOT_EXIST);
        }
        return assembleUserVo(user);
    }

    @Override
    public boolean updateUserInfo(UserVo user) throws BusinessException
    {
        return false;
    }

    @Override
    public boolean resetPassword(String passwordOld, String passwordNew, User user) throws BusinessException
    {
        return false;
    }


    private UserVo assembleUserVo(User user){
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setUsername(user.getUsername());
        userVo.setEmail(user.getEmail());
        userVo.setDescription(user.getDescription());
        userVo.setPhone(user.getPhone());
        userVo.setRole(Const.ROLE.getName(user.getRole()));
        if(user.getBirthday() != null){
            userVo.setBirthday(user.getBirthday().getTime());
        }

        userVo.setShown(Const.VISIBILITY.getBool(user.getShown()));
        userVo.setAvatarUrl(user.getAvatarUrl());
        userVo.setCreateTime(user.getCreateTime().getTime());
        userVo.setUpdateTime(user.getUpdateTime().getTime());

        return userVo;
    }

}
