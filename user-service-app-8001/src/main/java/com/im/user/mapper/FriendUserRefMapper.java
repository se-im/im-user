package com.im.user.mapper;

import com.im.user.entity.po.FriendUserRef;
import com.im.user.entity.vo.FriendUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FriendUserRefMapper
{
    int deleteByPrimaryKey(Long id);

    int insert(FriendUserRef record);

    int insertSelective(FriendUserRef record);

    FriendUserRef selectByPrimaryKey(Long id);

    FriendUserVo selectByUserIdFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId);

    List<FriendUserVo> selectByUserId(Long userId);

    int updateByPrimaryKeySelective(FriendUserRef record);

    int updateByPrimaryKey(FriendUserRef record);

    int deleteLogicByUserIdFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId);
}