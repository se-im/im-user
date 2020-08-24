package com.im.user.mapper;

import com.im.user.entity.po.GroupMemberPo;

public interface GroupMemberMapper
{
    int deleteByPrimaryKey(Long id);

    int insert(GroupMemberPo record);

    int insertSelective(GroupMemberPo record);

    GroupMemberPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GroupMemberPo record);

    int updateByPrimaryKey(GroupMemberPo record);
}