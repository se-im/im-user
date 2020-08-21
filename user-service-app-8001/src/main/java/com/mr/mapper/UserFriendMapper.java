package com.mr.mapper;

import com.mr.entity.po.UserFriend;
import com.mr.entity.vo.UserFriendVo;

import java.util.List;

public interface UserFriendMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserFriend record);

    int insertSelective(UserFriend record);

    UserFriend selectByPrimaryKey(Long id);

    List<UserFriendVo> selectByUserId(Long userId);

    int updateByPrimaryKeySelective(UserFriend record);

    int updateByPrimaryKey(UserFriend record);
}