package com.mr.service;

import com.mr.entity.vo.ReceivedFriendQeuestVo;
import com.mr.entity.vo.SendedFriendRequestVo;
import com.mr.entity.vo.UserVo;
import com.mr.response.error.BusinessException;
import com.mr.entity.po.User;
import org.springframework.stereotype.Service;

import java.util.List;


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
     */
    UserVo getUserById(Long userId) throws BusinessException;

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


    /**
     * 根据用户名或id搜索用户
     */
    public List<UserVo> fuzzyQuery(String query) throws BusinessException;

    /**
     * - 发送添加好友请求
     */
    public String addFriend(Long userId);

    /**
     * - 查询我收到的好友请求
     */
    public ReceivedFriendQeuestVo queryFriendRequestReceived();

    /**
     *- 查询我发送的好友请求
     */
    public SendedFriendRequestVo queryFriendRequestSended();

    /**
     *- 处理好友请求
     */
    public String processMyFriendRequest(Long requestId, Long status);

    /**
     *- 查询我的好友
     */
    public UserVo queryMyFriend();


    /**
     *- 删除好友
     */
    public String deleteFriend(Long friendId);

}
