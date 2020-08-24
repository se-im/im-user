package com.im.user.service.impl;

import com.im.user.entity.po.GroupMemberPo;
import com.im.user.entity.po.GroupPo;
import com.im.user.entity.po.User;
import com.im.user.entity.request.GroupUpdateRequest;
import com.im.user.entity.vo.GroupBriefVo;
import com.im.user.entity.vo.GroupUserBriefVo;
import com.im.user.entity.vo.UserVo;
import com.im.user.exception.BusinessErrorEnum;
import com.im.user.mapper.GroupMapper;
import com.im.user.mapper.GroupMemberMapper;
import com.im.user.mapper.UserMapper;
import com.im.user.service.IGroupService;
import com.im.user.service.IUserService;
import com.mr.response.error.BusinessException;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Component
@Service
public class GroupServiceImpl implements IGroupService
{

    @Resource
    private GroupMapper groupMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private GroupMemberMapper groupMemberMapper;

    @Override
    @Transactional
    public Long createGroup(Long creatorId, List<Long> initialUserIds) throws BusinessException
    {

        User creatorUser = userMapper.selectByPrimaryKey(creatorId);
        if(creatorUser == null)
        {
            throw new BusinessException("创建者id:" + creatorId+ "对应的用户不存在");
        }

        List<User>  initialUsers = new ArrayList<>();

        for (Long id: initialUserIds)
        {
            User user = userMapper.selectByPrimaryKey(id);
            if(user == null)
            {
                throw new BusinessException("群成员id:" +id + "对应的用户不存在");
            }
            initialUsers.add(user);
        }



        //插入群表
        GroupPo groupPo = GroupPo.builder()
                .createUserId(creatorId)
                .memberNum(initialUserIds.size() + 1)
                .name( creatorUser.getUsername() + "的群聊")
                .build();

        int res = groupMapper.insertSelective(groupPo);
        if(res < 1)
        {
            throw new BusinessException("创建群聊失败");
        }

        Long groupId = groupPo.getId();

        //插入群成员表
        for(User user: initialUsers)
        {
            GroupMemberPo groupMemberPo = GroupMemberPo.builder()
                    .groupId(groupId)
                    .userId(user.getId())
                    .userName(user.getUsername())
                    .userAvatarUrl(user.getAvatarUrl())
                    .build();
            res = groupMemberMapper.insertSelective(groupMemberPo);
            if(res < 1)
            {
                throw new BusinessException("插入群成员失败");
            }
        }
        return groupId;
    }


    @Override
    public List<GroupBriefVo> queryJoinedGroup(Long userId) {
        List<GroupBriefVo> groupBriefVos = groupMemberMapper.selectByGroupMemberUserId(userId);
        return groupBriefVos;
    }

    @Override
    public List<GroupUserBriefVo> queryGroupUsers(Long groupId) {
        List<GroupUserBriefVo> groupUserBriefVos = groupMemberMapper.selectByGroupId(groupId);
        return groupUserBriefVos;
    }

    @Override
    public void updateGroupInfo(GroupUpdateRequest groupUpdateRequest) throws BusinessException {
        GroupPo groupPo = GroupPo.builder().build();
        BeanUtils.copyProperties(groupPo,groupUpdateRequest);
        int res = groupMapper.updateByPrimaryKeySelective(groupPo);
        if(res < 1)
        {
            throw new BusinessException("修改群信息失败");
        }
    }

    @Override
    public GroupPo queryGroupById(Long groupId)
    {
        return groupMapper.selectByPrimaryKey(groupId);
    }
}
