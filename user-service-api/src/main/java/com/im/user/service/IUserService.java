package com.im.user.service;

import com.im.user.entity.po.User;
import com.im.user.entity.vo.UserVo;
import com.mr.response.error.BusinessException;


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
     * 根据token获取当前User
     */
    UserVo getUserByToken(String token) throws BusinessException;

    /**
     * 根据id获取用户
     * @return
     */
    User getUserById(Long userId) throws BusinessException;

    /**
     * 更新用户信息
     */
    boolean updateUserInfo(User user) throws BusinessException;

    /**
     * 重置密码
     */
    boolean resetPassword(String passwordOld, String passwordNew, UserVo userVo) throws BusinessException;


    /**
     *用户注销
     */
    boolean deleteUser(UserVo userVo) throws BusinessException;




}
