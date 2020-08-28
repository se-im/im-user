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

    /**
     * 查询当前用户的某个好友
     * @param userId
     * @param friendId
     * @return
     */
    List<FriendUserVo> selectFriendVoByFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId);

    List<FriendUserVo> selectByUserId(Long userId);

    int updateByPrimaryKeySelective(FriendUserRef record);

    int updateByPrimaryKey(FriendUserRef record);

    int deleteLogicByUserIdFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId);

    int updateByUserIdFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId,@Param("note") String note);

    String selectNoteByUserIdFriendId(@Param("userId") Long userId, @Param("friendId") Long friendId);
}