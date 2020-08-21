package com.mr.mapper;

import com.mr.entity.po.UserFriend;
import com.mr.entity.vo.UserFriendVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFriendMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserFriend record);

    int insertSelective(UserFriend record);

    UserFriend selectByPrimaryKey(Long id);

    UserFriendVo selectByUserIdFriendId(@Param("userId") Long userId,@Param("friendId") Long friendId);

    List<UserFriendVo> selectByUserId(Long userId);

    int updateByPrimaryKeySelective(UserFriend record);

    int updateByPrimaryKey(UserFriend record);

    int deleteLogicByUserIdFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId);
}