package com.im.user.service;

import com.im.user.entity.po.GroupPo;
import com.im.user.entity.request.GroupUpdateRequest;
import com.im.user.entity.vo.GroupBriefVo;
import com.im.user.entity.vo.GroupUserBriefVo;
import com.mr.response.ServerResponse;
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


    /**
     * 查询当前用户加入的所有群
     * @param userId
     * @return
     */
    public List<GroupBriefVo> queryJoinedGroup(Long userId);

    /**
     * 根据群Id查询该群所有群成员
     * @param groupId
     * @return
     */
    public List<GroupUserBriefVo> queryGroupUsers(Long groupId);


    public GroupPo queryGroupById(Long groupId);

    public void updateGroupInfo(GroupUpdateRequest groupUpdateRequest) throws BusinessException;
}
