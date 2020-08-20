package com.mr.mapper;

import com.mr.entity.po.UserFriendRequest;

public interface UserFriendRequestMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserFriendRequest record);

    int insertSelective(UserFriendRequest record);

    UserFriendRequest selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserFriendRequest record);

    int updateByPrimaryKey(UserFriendRequest record);
}