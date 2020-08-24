package com.mr.mapper;

import com.mr.entity.po.FriendUser;
import com.mr.entity.vo.FriendUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FriendUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FriendUser record);

    int insertSelective(FriendUser record);

    FriendUser selectByPrimaryKey(Long id);

    FriendUserVo selectByUserIdFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId);

    List<FriendUserVo> selectByUserId(Long userId);

    int updateByPrimaryKeySelective(FriendUser record);

    int updateByPrimaryKey(FriendUser record);

    int deleteLogicByUserIdFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId);
}