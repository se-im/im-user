package com.mr.mapper;

import com.mr.entity.po.AddFriendRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFriendRequestMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AddFriendRequest record);

    int insertSelective(AddFriendRequest record);

    AddFriendRequest selectByPrimaryKey(Long id);

    AddFriendRequest selectBySenderIdReceiverId(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);

    List<AddFriendRequest> selectByReceiverId(Long receiverId);

    List<AddFriendRequest> selectBySenderId(Long senderId);

    int updateByPrimaryKeySelective(AddFriendRequest record);

    int updateByPrimaryKey(AddFriendRequest record);

    int updateStatusByPrimaryKey(@Param("id") Long id, @Param("status") Integer status);
}