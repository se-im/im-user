package com.mr.mapper;

import com.mr.entity.po.UserFriendRequest;

import java.util.List;

public interface UserFriendRequestMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserFriendRequest record);

    int insertSelective(UserFriendRequest record);

    UserFriendRequest selectByPrimaryKey(Long id);

    List<UserFriendRequest> selectByReceiverId(Long receiverId);

    List<UserFriendRequest> selectBySenderId(Long senderId);

    int updateByPrimaryKeySelective(UserFriendRequest record);

    int updateByPrimaryKey(UserFriendRequest record);
}