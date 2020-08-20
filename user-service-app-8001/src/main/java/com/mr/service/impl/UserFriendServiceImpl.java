package com.mr.service.impl;

import com.mr.common.UserConst;
import com.mr.common.UserFriendConst;
import com.mr.entity.po.User;
import com.mr.entity.po.UserFriend;
import com.mr.entity.po.UserFriendRequest;
import com.mr.entity.vo.ReceivedFriendQeuestVo;
import com.mr.entity.vo.SendedFriendRequestVo;
import com.mr.entity.vo.UserVo;
import com.mr.exception.BusinessErrorEnum;
import com.mr.mapper.UserFriendMapper;
import com.mr.mapper.UserFriendRequestMapper;
import com.mr.mapper.UserMapper;
import com.mr.response.error.BusinessException;
import com.mr.service.IUserFriendService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Service
@Slf4j
public class UserFriendServiceImpl implements IUserFriendService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserFriendRequestMapper userFriendRequestMapper;

    @Resource
    private UserFriendMapper userFriendMapper;
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
    public void addFriend(User currentUser,Long friendUserIdTobeAdded,String note) throws BusinessException {
        User friendUser = userMapper.selectByPrimaryKey(friendUserIdTobeAdded);
        if(friendUser == null){
            throw new BusinessException(BusinessErrorEnum.USER_NOT_EXIST);
        }
        UserFriendRequest userFriendRequest = new UserFriendRequest();
        userFriendRequest.setSenderId(currentUser.getId());
        userFriendRequest.setReceiverId(friendUserIdTobeAdded);
        userFriendRequest.setStatus(UserFriendConst.UserFriendStatus.BEVERIFIED_STATUS.getCode());
        userFriendRequest.setNote(note);

        userFriendRequest.setSenderUsername(currentUser.getUsername());
        userFriendRequest.setSenderAvatarUrl(currentUser.getAvatarUrl());
        userFriendRequest.setReceiverUsername(friendUser.getUsername());
        userFriendRequest.setReceiverAvatarUrl(friendUser.getAvatarUrl());
        int res = userFriendRequestMapper.insert(userFriendRequest);
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
    public List<ReceivedFriendQeuestVo> queryFriendRequestReceived(User currentUser) {
        List<UserFriendRequest> userFriendRequests = userFriendRequestMapper.selectByReceiverId(currentUser.getId());
        if(userFriendRequests == null){
            return null;
        }
        List<ReceivedFriendQeuestVo> receivedFriendQeuestVos = new ArrayList<ReceivedFriendQeuestVo>();
        for(UserFriendRequest userFriendRequest : userFriendRequests){
            receivedFriendQeuestVos.add(assembleReceivedFriendQeuestVo(userFriendRequest));
        }
        return receivedFriendQeuestVos;
    }

    /**
     * 查询当前用户发送的好友请求
     * @param currentUser
     * @return
     */
    @Override
    public List<SendedFriendRequestVo> queryFriendRequestSended(User currentUser) {
        List<UserFriendRequest> userFriendRequests = userFriendRequestMapper.selectBySenderId(currentUser.getId());
        if(userFriendRequests == null){
            return null;
        }
        List<SendedFriendRequestVo> sendedFriendRequestVos = new ArrayList<SendedFriendRequestVo>();
        for(UserFriendRequest userFriendRequest : userFriendRequests){
            sendedFriendRequestVos.add(assembleSendedFriendRequestVo(userFriendRequest));
        }
        return sendedFriendRequestVos;
    }


    @Override
    public String processMyFriendRequest(Long requestId, Long status) {
        return null;
    }

    @Override
    public UserVo queryMyFriend() {
        return null;
    }

    @Override
    public String deleteFriend(Long friendId) {
        return null;
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

    private ReceivedFriendQeuestVo assembleReceivedFriendQeuestVo(UserFriendRequest userFriendRequest)
    {
        ReceivedFriendQeuestVo receivedFriendQeuestVo = new ReceivedFriendQeuestVo();
        receivedFriendQeuestVo.setSenderId(userFriendRequest.getSenderId());
        receivedFriendQeuestVo.setStatus(userFriendRequest.getStatus());
        receivedFriendQeuestVo.setNote(userFriendRequest.getNote());
        receivedFriendQeuestVo.setSenderUsername(userFriendRequest.getSenderUsername());
        receivedFriendQeuestVo.setSenderAvatarUrl(userFriendRequest.getSenderAvatarUrl());
        return receivedFriendQeuestVo;
    }

    private SendedFriendRequestVo assembleSendedFriendRequestVo(UserFriendRequest userFriendRequest)
    {
        SendedFriendRequestVo sendedFriendRequestVo = new SendedFriendRequestVo();
        sendedFriendRequestVo.setReceiverId(userFriendRequest.getReceiverId());
        sendedFriendRequestVo.setStatus(userFriendRequest.getStatus());
        sendedFriendRequestVo.setNote(userFriendRequest.getNote());
        sendedFriendRequestVo.setReceiverUsername(userFriendRequest.getReceiverUsername());
        sendedFriendRequestVo.setReceiverAvatarUrl(userFriendRequest.getReceiverAvatarUrl());

        return sendedFriendRequestVo;
    }
}
