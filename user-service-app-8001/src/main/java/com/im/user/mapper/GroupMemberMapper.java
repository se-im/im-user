package com.im.user.mapper;

import com.im.user.entity.po.GroupMemberPo;
import com.im.user.entity.po.GroupPo;

public interface GroupMemberMapper
{
    int deleteByPrimaryKey(Long id);

    int insert(GroupMemberPo record);

    int insertSelective(GroupMemberPo record);

    GroupMemberPo selectByPrimaryKey(Long id);

    GroupPo selectByGroupMemberUserId(Long groupMemberUserId);

    int updateByPrimaryKeySelective(GroupMemberPo record);

    int updateByPrimaryKey(GroupMemberPo record);
}