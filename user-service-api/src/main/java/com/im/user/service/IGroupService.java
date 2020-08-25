package com.im.user.service;

import com.im.user.entity.po.GroupPo;
import com.im.user.entity.request.GroupUpdateRequest;
import com.im.user.entity.vo.GroupBriefVo;
import com.im.user.entity.vo.GroupUserBriefVo;
import com.im.user.entity.vo.GroupVo;
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
     */
    public List<GroupBriefVo> queryJoinedGroup(Long userId);

    /**
     * 根据群Id查询该群所有群成员
     */
    public List<GroupUserBriefVo> queryGroupUsers(Long groupId);

    public GroupPo queryGroupById(Long groupId);

    public void updateGroupInfo(GroupUpdateRequest groupUpdateRequest) throws BusinessException;

    /**
     * 给某个群添加群成员
     */
    public void insertGroupUser(Long groupId, List<Long> insertUserIds) throws BusinessException;

    public void withdrawFromGroup(Long userId,Long groupId) throws BusinessException;

}
