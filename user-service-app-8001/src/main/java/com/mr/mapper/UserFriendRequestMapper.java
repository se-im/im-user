package com.mr.mapper;

import com.mr.entity.po.UserFriendRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFriendRequestMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserFriendRequest record);

    int insertSelective(UserFriendRequest record);

    UserFriendRequest selectByPrimaryKey(Long id);

    UserFriendRequest selectBySenderIdReceiverId(@Param("senderId") Long senderId,@Param("receiverId") Long receiverId);

    List<UserFriendRequest> selectByReceiverId(Long receiverId);

    List<UserFriendRequest> selectBySenderId(Long senderId);

    int updateByPrimaryKeySelective(UserFriendRequest record);

    int updateByPrimaryKey(UserFriendRequest record);

    int updateStatusByPrimaryKey(@Param("id") Long id, @Param("status") Integer status);
}