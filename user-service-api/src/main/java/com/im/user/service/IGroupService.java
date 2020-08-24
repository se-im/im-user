package com.im.user.service;

import com.im.user.entity.vo.GroupBriefVo;
import com.mr.response.error.BusinessException;

import java.util.List;

public interface IGroupService
{
    /**
     * 创建群聊
     * @param creatorId  创建人的id
     * @param initialUserIds  初始用户的id
     * @return 创建成功后群的id
     */
    public Long createGroup(Long creatorId, List<Long> initialUserIds) throws BusinessException;



    public List<GroupBriefVo> queryJoinedGroup(Long userId);
}