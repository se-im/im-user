package com.mr.service;

import com.mr.entity.po.User;
import com.mr.entity.vo.ReceivedFriendQeuestVo;
import com.mr.entity.vo.SendedFriendRequestVo;
import com.mr.entity.vo.FriendUserVo;
import com.mr.entity.vo.UserVo;
import com.mr.response.error.BusinessException;

import java.util.List;

public interface IFriendService {

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

    public SendedFriendRequestVo queryFriendRequestSendedDetail(User currentUser,Long friendUserIdTobeAdded);

    /**
     *- 处理好友请求
     */
    public void processMyFriendRequest(User currentUser,Long requestId, Integer status) throws BusinessException ;

    /**
     *- 查询我的好友
     */
    public List<FriendUserVo> queryMyFriend(User currentUser);

    /**
     * 查询我的某个好友
     * @param currentUser
     * @param friendId
     * @return
     */
    public FriendUserVo queryFriendDetail(User currentUser, Long friendId);

    /**
     *- 删除好友
     */
    public void deleteFriend(User currentUser,Long friendId) throws BusinessException;

    /*
     *暴力添加好友
     */
    public void violenceAddFriend(Long currentUserId, Long friendId) throws BusinessException;
}
