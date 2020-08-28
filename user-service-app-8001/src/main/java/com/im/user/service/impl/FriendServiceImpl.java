package com.im.user.service.impl;

import com.im.user.entity.po.AddFriendRequest;
import com.im.user.entity.po.FriendUserRef;
import com.im.user.mapper.FriendUserRefMapper;
import com.mr.common.UserConst;
import com.mr.common.UserFriendConst;
import com.im.user.entity.po.User;
import com.im.user.entity.vo.ReceivedFriendRequestVo;
import com.im.user.entity.vo.SendedFriendRequestVo;
import com.im.user.entity.vo.FriendUserVo;
import com.im.user.entity.vo.UserVo;
import com.im.user.exception.BusinessErrorEnum;
import com.im.user.mapper.AddFriendRequestMapper;
import com.im.user.mapper.UserMapper;
import com.mr.response.error.BusinessException;
import com.im.user.service.IFriendService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Service
@Slf4j
public class FriendServiceImpl implements IFriendService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private AddFriendRequestMapper addFriendRequestMapper;

    @Resource
    private FriendUserRefMapper friendUserRefMapper;
    /**
     * 根据用户名或用户id查询
     * @param query
     * @return
     */
    @Override
    public List<UserVo> fuzzyQuery(String query){
        List<UserVo> userVoList = new ArrayList<UserVo>();
        try {
            long l = Long.parseLong(query);
            User user = userMapper.selectByPrimaryKey(l);
            if(user != null){
                userVoList.add(assembleUserVo(user));
            }
        }catch (Exception e){
            User user = userMapper.selectUserByUsername(query);
            if(user != null){
                userVoList.add(assembleUserVo(user));
            }
        }
        return userVoList;
    }


    /**
     * 发送添加好友请求
     * @param currentUser
     * @param friendUserIdTobeAdded
     * @param note
     * @throws BusinessException
     */
    @Override
    public void addFriend(User currentUser, Long friendUserIdTobeAdded, String note) throws BusinessException {
        User friendUser = userMapper.selectByPrimaryKey(friendUserIdTobeAdded);
        if(friendUser == null){
            throw new BusinessException(BusinessErrorEnum.USER_NOT_EXIST);
        }
        AddFriendRequest addFriendRequest = new AddFriendRequest();
        addFriendRequest.setSenderId(currentUser.getId());
        addFriendRequest.setReceiverId(friendUserIdTobeAdded);
        addFriendRequest.setStatus(UserFriendConst.UserFriendStatus.BEVERIFIED_STATUS.getCode());
        addFriendRequest.setNote(note);

        addFriendRequest.setSenderUsername(currentUser.getUsername());
        addFriendRequest.setSenderAvatarUrl(currentUser.getAvatarUrl());
        addFriendRequest.setReceiverUsername(friendUser.getUsername());
        addFriendRequest.setReceiverAvatarUrl(friendUser.getAvatarUrl());
        int res = addFriendRequestMapper.insert(addFriendRequest);
        if(res == 0){
            throw new BusinessException("发送好友请求失败");
        }
    }

    /**
     * 查询当前用户收到的好友请求
     * @param currentUser
     * @return
     */
    @Override
    public List<ReceivedFriendRequestVo> queryFriendRequestReceived(User currentUser) {
        List<AddFriendRequest> addFriendRequests = addFriendRequestMapper.selectByReceiverId(currentUser.getId());
        if(addFriendRequests == null){
            return null;
        }
        List<ReceivedFriendRequestVo> ReceivedFriendRequestVos = new ArrayList<ReceivedFriendRequestVo>();
        for(AddFriendRequest addFriendRequest : addFriendRequests){
            ReceivedFriendRequestVos.add(assembleReceivedFriendQeuestVo(addFriendRequest));
        }
        return ReceivedFriendRequestVos;
    }

    /**
     * 查询当前用户发送的好友请求
     * @param currentUser
     * @return
     */
    @Override
    public List<SendedFriendRequestVo> queryFriendRequestSended(User currentUser) {
        List<AddFriendRequest> addFriendRequests = addFriendRequestMapper.selectBySenderId(currentUser.getId());
        if(addFriendRequests == null){
            return null;
        }
        List<SendedFriendRequestVo> sendedFriendRequestVos = new ArrayList<SendedFriendRequestVo>();
        for(AddFriendRequest addFriendRequest : addFriendRequests){
            sendedFriendRequestVos.add(assembleSendedFriendRequestVo(addFriendRequest));
        }
        return sendedFriendRequestVos;
    }

    @Override
    public SendedFriendRequestVo queryFriendRequestSendedDetail(User currentUser, Long friendUserIdTobeAdded){
        AddFriendRequest addFriendRequest = addFriendRequestMapper.selectBySenderIdReceiverId(currentUser.getId(), friendUserIdTobeAdded);
        if(addFriendRequest == null){
            return null;
        }
        SendedFriendRequestVo sendedFriendRequestVo = assembleSendedFriendRequestVo(addFriendRequest);
        return sendedFriendRequestVo;
    }
    @Override
    public void processMyFriendRequest(User currentUser, Long requestId, Integer status) throws BusinessException {
        AddFriendRequest addFriendRequest = addFriendRequestMapper.selectByPrimaryKey(requestId);
        Integer userFriendRequestStatus = addFriendRequest.getStatus();
        if(userFriendRequestStatus != 0 && userFriendRequestStatus != 3){
            throw new BusinessException("好友请求已处理过！");
        }
        //校验status参数是否为1/2
        if(status != 1 && status != 2){
            return;
        }
        //更新status字段值
        int res = addFriendRequestMapper.updateStatusByPrimaryKey(requestId, status);
        if(res == 0){
            throw new BusinessException("更新状态失败");
        }
        //如果拒绝直接返回，否则进行后续处理
        if(status == 1){
            return;
        }
        //添加两条记录（单独抽方法）
        agreeFriendRequest(currentUser,requestId);
    }

    @Transactional
    public void agreeFriendRequest(User currentUser, Long requestId) throws BusinessException {
        AddFriendRequest addFriendRequest = addFriendRequestMapper.selectByPrimaryKey(requestId);
        Long senderId = addFriendRequest.getSenderId();
        violenceAddFriend(currentUser.getId(), senderId);
    }

    /**
     * 暴力添加好友
     */
    public void violenceAddFriend(Long currentUserId, Long friendId) throws BusinessException
    {

        log.info("用户{}添加用户{}为好友", currentUserId, friendId);

        List<FriendUserVo> friendUserVos = friendUserRefMapper.selectFriendVoByFriendId(currentUserId, friendId);
        if(friendUserVos != null && friendUserVos.size() > 0)
        {
            throw new BusinessException("已是好友，无法重复添加");
        }

        User currentUser = userMapper.selectByPrimaryKey(currentUserId);
        User requestUser = userMapper.selectByPrimaryKey(friendId);

        FriendUserRef userFriendCurrentUser = new FriendUserRef();
        userFriendCurrentUser.setUserId(currentUserId);
        userFriendCurrentUser.setFriendId(friendId);
        userFriendCurrentUser.setNote(requestUser.getUsername());
        userFriendCurrentUser.setDeleted(UserFriendConst.UserFriendDeleted.NONDELETED.getCode());

        FriendUserRef friendUserRef = new FriendUserRef();
        friendUserRef.setUserId(friendId);
        friendUserRef.setFriendId(currentUserId);
        friendUserRef.setNote(currentUser.getUsername());
        friendUserRef.setDeleted(UserFriendConst.UserFriendDeleted.NONDELETED.getCode());
        int res = friendUserRefMapper.insertSelective(userFriendCurrentUser);
        if(res ==0){
            throw new BusinessException("添加好友失败！");
        }
        int res1 = friendUserRefMapper.insertSelective(friendUserRef);
        if(res1 == 0){
            throw new BusinessException("添加好友失败！");
        }
    }

    @Override
    public List<FriendUserVo> queryMyFriend(User currentUser) {
        List<FriendUserVo> friendUserVos = friendUserRefMapper.selectByUserId(currentUser.getId());
        return friendUserVos;
    }

    @Override
    public FriendUserVo queryFriendDetail(User currentUser, Long friendId) {
        List<FriendUserVo> friendUserVos = friendUserRefMapper.selectFriendVoByFriendId(currentUser.getId(), friendId);
        if(friendUserVos == null || friendUserVos.size() == 0)
        {
            return null;
        }
        return friendUserVos.get(0);
    }

    @Override
    public void deleteFriend(User currentUser, Long friendId) throws BusinessException {
        deleteFriendTwo(currentUser,friendId);
        return ;
    }

    @Transactional
    public int deleteFriendTwo(User currentUser, Long friendId) throws BusinessException {
        int res = friendUserRefMapper.deleteLogicByUserIdFriendId(currentUser.getId(), friendId);
        if(res ==0){
            throw new BusinessException("删除好友失败！");
        }
        int res1 = friendUserRefMapper.deleteLogicByUserIdFriendId(friendId, currentUser.getId());
        if(res1 ==0){
            throw new BusinessException("删除好友失败！");
        }
        return res;

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

    private ReceivedFriendRequestVo assembleReceivedFriendQeuestVo(AddFriendRequest addFriendRequest)
    {
        ReceivedFriendRequestVo receivedFriendRequestVo = new ReceivedFriendRequestVo();
        receivedFriendRequestVo.setSenderId(addFriendRequest.getSenderId());
        receivedFriendRequestVo.setStatus(addFriendRequest.getStatus());
        receivedFriendRequestVo.setNote(addFriendRequest.getNote());
        receivedFriendRequestVo.setSenderUsername(addFriendRequest.getSenderUsername());
        receivedFriendRequestVo.setSenderAvatarUrl(addFriendRequest.getSenderAvatarUrl());
        receivedFriendRequestVo.setRequestId(addFriendRequest.getId());
        return receivedFriendRequestVo;
    }

    private SendedFriendRequestVo assembleSendedFriendRequestVo(AddFriendRequest addFriendRequest)
    {
        SendedFriendRequestVo sendedFriendRequestVo = new SendedFriendRequestVo();
        sendedFriendRequestVo.setReceiverId(addFriendRequest.getReceiverId());
        sendedFriendRequestVo.setStatus(addFriendRequest.getStatus());
        sendedFriendRequestVo.setNote(addFriendRequest.getNote());
        sendedFriendRequestVo.setReceiverUsername(addFriendRequest.getReceiverUsername());
        sendedFriendRequestVo.setReceiverAvatarUrl(addFriendRequest.getReceiverAvatarUrl());

        return sendedFriendRequestVo;
    }
}
