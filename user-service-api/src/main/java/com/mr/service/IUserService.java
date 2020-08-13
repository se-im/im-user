package com.mr.service;

import com.mr.entity.vo.UserVo;
import com.mr.response.error.BusinessException;
import com.mr.entity.po.User;
import org.springframework.stereotype.Service;


public interface IUserService {

    /**
     * 登陆，成功返回token，失败抛异常
     */
    String login(String username, String password) throws BusinessException;

    /**
     * 注册用户
     */
    void register(User user) throws BusinessException;

    /**
     * 根据token获取User
     */
    UserVo getUserByToken(String token) throws BusinessException;

    /**
     * 根据id获取用户
     */
    UserVo getUserById(Long userId, Integer namespace) throws BusinessException;

    /**
     * 更新用户信息
     */
    boolean updateUserInfo(UserVo user) throws BusinessException;

    /**
     * 重置密码
     */
    boolean resetPassword(String passwordOld, String passwordNew, UserVo userVo) throws BusinessException;


}
