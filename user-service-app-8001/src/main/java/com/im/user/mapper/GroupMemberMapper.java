package com.im.user.mapper;

import com.im.user.entity.po.GroupMemberPo;
import com.im.user.entity.po.GroupPo;
import com.im.user.entity.vo.GroupBriefVo;
import com.im.user.entity.vo.GroupUserBriefVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupMemberMapper
{
    int deleteByPrimaryKey(Long id);

    int insert(GroupMemberPo record);

    int insertSelective(GroupMemberPo record);

    GroupMemberPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GroupMemberPo record);

    int updateByPrimaryKey(GroupMemberPo record);

    List<GroupBriefVo> selectByGroupMemberUserId(Long groupMemberUserId);

    List<GroupUserBriefVo> selectByGroupId(Long groupId);

    /**
     * 查询某用户添加的某个群的群信息
     * @param groupId
     * @param groupMemberUserId
     * @return
     */
    GroupBriefVo selectByGroupIdGroupMemberUserId(@Param("groupId") Long groupId,@Param("groupMemberUserId") Long groupMemberUserId);

    int deleteLogicGroupMember(@Param("groupId") Long groupId,@Param("groupMemberUserId") Long groupMemberUserId);

    int updateRedundantByuserId(@Param("userId") Long userId,@Param("userName") String userName,@Param("avatarUal") String avatarUrl);
}