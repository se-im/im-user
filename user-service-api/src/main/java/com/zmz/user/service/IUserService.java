package com.zmz.user.service;

import com.zmz.response.error.BusinessException;
import com.zmz.user.entity.po.User;


public interface IUserService {

    /**
     * 登陆，成功返回token，失败抛异常
     * @throws BusinessException
     */
    String login(String username, String password) throws BusinessException;
    User register(User user) throws BusinessException;
    User getUserByToken(String token);
    User selectUserById(Integer userId) throws BusinessException;
    User updateInformation(User user) throws BusinessException;
    void resetPassword(String passwordOld, String passwordNew, User user) throws BusinessException;
    String selectQuestion(String username) throws BusinessException;

}
