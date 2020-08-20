package com.mr.service.impl;

import com.mr.common.UserConst;
import com.mr.common.JwtToken;
import com.mr.common.RedisPrefixConst;
import com.mr.constant.TokenHashConst;
import com.mr.entity.vo.UserVo;
import com.mr.exception.BusinessErrorEnum;
import com.mr.mapper.UserMapper;
import com.mr.response.error.BusinessException;
import com.mr.entity.po.User;
import com.mr.service.IUserService;
import com.mr.util.*;
import lombok.extern.slf4j.Slf4j;
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
public class UserServiceImpl implements IUserService
{

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
    public String login(String username, String password) throws BusinessException
    {

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
        {
            throw new BusinessException("用户名或密码不能为空");
        }
        User user = userMapper.selectUserByUsername(username);
        if (user == null)
        {
            throw new BusinessException(BusinessErrorEnum.INVALID_USERNAME_OR_PASSWORD);

        }
        if (user.getDeleted() == 1)
        {
            throw new BusinessException(BusinessErrorEnum.LOGFF_USER);
        }
        String passwordInDb = userMapper.selectPasswordByUsername(username);
        if (passwordInDb == null)
        {
            log.warn("A user {} which is not exist tried to login", username);
            throw new BusinessException(BusinessErrorEnum.INVALID_USERNAME_OR_PASSWORD);
        }

        String md5Password = MD5Util.MD5EncodeUtf8(password);

        if (!md5Password.equals(passwordInDb))
        {
            log.warn("A user {} with invalid password {} tried to login", username, password);
            throw new BusinessException(BusinessErrorEnum.INVALID_USERNAME_OR_PASSWORD);
        }

        String token = null;
        try
        {
            token = JwtToken.createToken();
        } catch (Exception e)
        {
            log.warn(String.valueOf(e.getCause()));
            throw new BusinessException("token创建失败");
        }

        redisTemplate.opsForHash().put(RedisPrefixConst.TOKEN_PREFIX + token, TokenHashConst.USER, user);
        redisTemplate.expire(RedisPrefixConst.TOKEN_PREFIX + token, expiration, TimeUnit.MINUTES);

        return token;
    }

    /**
     * 注册用户
     *
     * @param user
     * @return
     * @throws BusinessException
     */

    @Override
    public void register(User user) throws BusinessException
    {

        //1. 判断参数
        if (StringUtils.isEmpty(user.getUsername()))
        {
            throw new BusinessException(BusinessErrorEnum.USERNAME_EMPTY_ERROR);
        }
        if (StringUtils.isEmpty(user.getPassword()))
        {
            throw new BusinessException("密码不能为空！");
        }
        //2. 处理参数
        checkRegisterUserParam(user);
        User user1 = userMapper.selectUserByUsername(user.getUsername());
        if (user1 != null)
        {
            throw new BusinessException(BusinessErrorEnum.USER_EXIST);
        }
        //2. 添加
        //3. 判断是否插入成功
        int resultCount = userMapper.insertSelective(user);
        if (resultCount == 0)
        {
            throw new BusinessException(BusinessErrorEnum.REGISTER_FAILED);
        }
    }

    /**
     * 用户注册参数校验
     */
    private void checkRegisterUserParam(User user) throws BusinessException
    {

        if (user.getBirthday() != null)
        {
            Date date = new Date();
            if (date.before(user.getBirthday()))
            {
                throw new BusinessException("生日不合法！");
            }
        }
        user.setId(null);
        user.setRole(UserConst.ROLE.ROLE_CUSTOMER.getCode());
        user.setDeleted(UserConst.UserStatus.NONDELETED.getCode());
        user.setShown(UserConst.VISIBILITY.ALLOW.getCode());
        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
    }

    @Override
    public UserVo getUserByToken(String token) throws BusinessException
    {
        if (StringUtils.isEmpty(token))
        {
            throw new BusinessException("token不能为空");
        }

        Object o = redisTemplate.opsForHash().get(RedisPrefixConst.TOKEN_PREFIX + token, TokenHashConst.USER);
        if (o == null)
        {
            throw new BusinessException(BusinessErrorEnum.TOKEN_EXPIRED);
        }
        User user = (User) o;
        return assembleUserVo(user);
    }


    @Override
    public UserVo getUserById(Long userId) throws BusinessException
    {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null)
        {
            throw new BusinessException(BusinessErrorEnum.USER_NOT_EXIST);
        }
        return assembleUserVo(user);
    }

    @Override
    public boolean updateUserInfo(User userNew) throws BusinessException
    {

        //处理参数
        if (userNew.getBirthday() != null)
        {
            Date date = new Date();
            if (date.before(userNew.getBirthday()))
            {
                throw new BusinessException("生日不合法！");
            }
        }
        userNew.setPassword(null);
        userNew.setRole(UserConst.ROLE.ROLE_CUSTOMER.getCode());
        userNew.setDeleted(UserConst.UserStatus.NONDELETED.getCode());
        int res = userMapper.updateByPrimaryKeySelective(userNew);
        if (res == 0)
        {
            throw new BusinessException("修改信息失败！");
        }
        return true;
    }

    @Override
    public boolean resetPassword(String passwordOld, String passwordNew, UserVo userVo) throws BusinessException
    {
        User user = userMapper.selectUserByUsername(userVo.getUsername());
        //Md5加密旧密码
        String passwordOldMd5 = MD5Util.MD5EncodeUtf8(passwordOld);
        if (!user.getPassword().equals(passwordOldMd5))
        {
            throw new BusinessException(BusinessErrorEnum.INVALID_PASSWORD);
        }
        int res = userMapper.updatePasswordByPrimaryKey(MD5Util.MD5EncodeUtf8(passwordNew), user.getId());

        if (res <= 0)
        {
            throw new BusinessException("修改密码失败！");
        }
        return true;
    }


    @Override
    public boolean deleteUser(UserVo userVo) throws BusinessException
    {
        User user = userMapper.selectUserByUsername(userVo.getUsername());
        int res = userMapper.deleteLogicByPrimaryKey(user.getId());
        if (res == 0)
        {
            return false;
        }
        return true;
    }

    private UserVo assembleUserVo(User user)
    {
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setUsername(user.getUsername());
        userVo.setEmail(user.getEmail());
        userVo.setDescription(user.getDescription());
        userVo.setPhone(user.getPhone());
        if (user.getBirthday() != null)
        {
            userVo.setBirthday(user.getBirthday().getTime());
        }
        userVo.setShown(UserConst.VISIBILITY.getBool(user.getShown()));
        userVo.setAvatarUrl(user.getAvatarUrl());
        userVo.setCreateTime(user.getCreateTime().getTime());
        return userVo;
    }

}
