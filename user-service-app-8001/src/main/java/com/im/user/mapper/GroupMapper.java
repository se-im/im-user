package com.im.user.mapper;

import com.im.user.entity.po.GroupPo;
import com.im.user.entity.request.GroupUpdateRequest;
import org.apache.ibatis.annotations.Param;

public interface GroupMapper
{
    int deleteByPrimaryKey(Long id);

    int insert(GroupPo record);

    int insertSelective(GroupPo record);

    GroupPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GroupPo record);

    int updateByPrimaryKey(GroupPo record);

    int updateMemberNumByPrimaryKey(@Param("memberNum") Integer memberNum,@Param("groupId") Long groupId);

    int updateMemberNumsOptimistic(@Param("groupId") Long groupId, @Param("originalMemberNum") Integer originalMemberNum, @Param("newMemberNum") Integer newMemberNum);
}