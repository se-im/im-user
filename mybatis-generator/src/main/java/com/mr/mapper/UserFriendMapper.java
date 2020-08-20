package com.mr.mapper;

import com.mr.entity.po.UserFriend;

public interface UserFriendMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserFriend record);

    int insertSelective(UserFriend record);

    UserFriend selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserFriend record);

    int updateByPrimaryKey(UserFriend record);
}