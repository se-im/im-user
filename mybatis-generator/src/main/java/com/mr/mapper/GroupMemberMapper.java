package com.mr.mapper;

import com.mr.entity.po.GroupMember;

public interface GroupMemberMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GroupMember record);

    int insertSelective(GroupMember record);

    GroupMember selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GroupMember record);

    int updateByPrimaryKey(GroupMember record);
}