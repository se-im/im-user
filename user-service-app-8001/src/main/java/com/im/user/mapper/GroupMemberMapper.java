package com.im.user.mapper;

import com.im.user.entity.po.GroupMemberPo;
import com.im.user.entity.po.GroupPo;
import com.im.user.entity.vo.GroupBriefVo;
import com.im.user.entity.vo.GroupUserBriefVo;

import java.util.List;

public interface GroupMemberMapper
{
    int deleteByPrimaryKey(Long id);

    int insert(GroupMemberPo record);

    int insertSelective(GroupMemberPo record);

    GroupMemberPo selectByPrimaryKey(Long id);

    List<GroupBriefVo> selectByGroupMemberUserId(Long groupMemberUserId);

    List<GroupUserBriefVo> selectByGroupId(Long groupId);

    int updateByPrimaryKeySelective(GroupMemberPo record);

    int updateByPrimaryKey(GroupMemberPo record);
}