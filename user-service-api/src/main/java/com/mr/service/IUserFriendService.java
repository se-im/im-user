package com.mr.service;

import com.mr.entity.po.User;
import com.mr.entity.vo.ReceivedFriendQeuestVo;
import com.mr.entity.vo.SendedFriendRequestVo;
import com.mr.entity.vo.UserVo;
import com.mr.response.error.BusinessException;

import java.util.List;

public interface IUserFriendService {

    /**
     * 根据用户名或id搜索用户
     */
    public List<UserVo> fuzzyQuery(String query);

    /**
     * - 发送添加好友请求
     */
    public void addFriend(User user, Long friendUserIdTobeAdded, String note) throws BusinessException;

    /**
     * - 查询我收到的好友请求
     */
    public List<ReceivedFriendQeuestVo> queryFriendRequestReceived(User currentUser);

    /**
     *- 查询我发送的好友请求
     */
    public List<SendedFriendRequestVo> queryFriendRequestSended(User currentUser);

    /**
     *- 处理好友请求
     */
    public String processMyFriendRequest(Long requestId, Long status) throws BusinessException;

    /**
     *- 查询我的好友
     */
    public UserVo queryMyFriend();


    /**
     *- 删除好友
     */
    public String deleteFriend(Long friendId);
}
