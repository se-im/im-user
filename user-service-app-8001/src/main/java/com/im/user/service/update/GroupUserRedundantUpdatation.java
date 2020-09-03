package com.im.user.service.update;


import com.im.user.mapper.GroupMemberMapper;
import com.mr.response.ServerResponse;
import com.mr.response.error.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
@Slf4j
public class GroupUserRedundantUpdatation {

    @Resource
    private GroupMemberMapper groupMemberMapper;

    public void groupUserRedundantUpdatate(Long userId,String userName,String avatarUrl) throws BusinessException {
        int res = groupMemberMapper.updateRedundantByuserId(userId, userName, avatarUrl);

        if(res < 1){
            throw new BusinessException("群用户冗余信息更改失败！");
        }
    }

    public void groupUserTableRedundantUpdatate(Long groupId,String groupName,String avatarUrl) throws BusinessException {
        int res = groupMemberMapper.updateRedundantBygroupId(groupId, groupName, avatarUrl);
        if(res < 1){
            throw new BusinessException("群冗余信息更改失败！");
        }
    }

}

