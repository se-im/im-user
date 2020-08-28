package com.im.user.service;

import com.im.user.entity.vo.FriendUserVo;
import com.im.user.entity.po.User;
import com.im.user.entity.vo.ReceivedFriendRequestVo;
import com.im.user.entity.vo.SendedFriendRequestVo;
import com.im.user.entity.vo.UserVo;
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
     * - 查询我收到的好友请求R
     */
    public List<ReceivedFriendRequestVo> queryFriendRequestReceived(User currentUser);

    /**
     *- 查询我发送的好友请求
     */
    public List<SendedFriendRequestVo> queryFriendRequestSended(User currentUser);

    public SendedFriendRequestVo queryFriendRequestSendedDetail(User currentUser, Long friendUserIdTobeAdded);

    /**
     *- 处理好友请求
     */
    public void processMyFriendRequest(User currentUser, Long requestId, Integer status) throws BusinessException ;

    /**
     * 查询我的所有好友
     * @param currentUser
     * @return
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
     * 删除好友
     * @param currentUser
     * @param friendId
     * @throws BusinessException
     */
    public void deleteFriend(User currentUser, Long friendId) throws BusinessException;

    /**
     * 暴力添加好友
     * @param currentUserId
     * @param friendId
     * @throws BusinessException
     */
    public void violenceAddFriend(Long currentUserId, Long friendId) throws BusinessException;

    /**
     * 修改好友备注
     * @param currentUserId
     * @param friendId
     * @param note
     * @throws BusinessException
     */
    public void updateFriendNote(Long currentUserId,Long friendId,String note) throws BusinessException;

    /**
     * 查询当前用户某个好友的备注
     * @param currentUserId
     * @param friendId
     * @return
     * @throws BusinessException
     */
    public String queryFriendNote(Long currentUserId,Long friendId) throws BusinessException;
}
