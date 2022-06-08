package com.im.user.controller;


import com.im.user.annotation.CurrentUser;
import com.im.user.entity.po.GroupPo;
import com.im.user.entity.po.User;
import com.im.user.entity.request.GroupUpdateRequest;
import com.im.user.entity.vo.GroupBriefVo;
import com.im.user.entity.vo.GroupUserBriefVo;
import com.im.user.entity.vo.GroupVo;
import com.im.user.exception.BusinessErrorEnum;
import com.im.user.mq.MqProducer;
import com.im.user.service.IGroupService;
import com.im.user.service.update.GroupUserRedundantUpdatation;
import com.mr.response.ServerResponse;
import com.mr.response.error.BusinessException;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/group/")
@Api(tags = "群相关的api")
@CrossOrigin
public class GroupController
{

    @Autowired
    private IGroupService groupService;
    @Autowired
    private GroupUserRedundantUpdatation groupUserRedundantUpdatation;
    @Autowired
    private MqProducer mqProducer;

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    @ApiOperation(value = "创建群聊", consumes = "application/json", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "创建成功，返回群的id")
    })
    @PostMapping(value = "/create", consumes="application/json")
    public ServerResponse<Long> createGroup(@CurrentUser @ApiIgnore User user, @RequestBody @ApiParam(value = "群初始成员id列表")List<Long> groupUserIds) throws BusinessException
    {
        if(groupUserIds == null )
        {
            throw new BusinessException(BusinessErrorEnum.PARAMETER_VALIDATION_ERROR);
        }
        Long groupId = groupService.createGroup(user.getId(), groupUserIds);
        return ServerResponse.success(groupId);
    }

    @ApiOperation(value = "查找当前用户添加的所有群")
    @GetMapping("/joined")
    public ServerResponse<List<GroupBriefVo>> queryJoinedGroup(@CurrentUser @ApiIgnore User user)
    {
        List<GroupBriefVo> groupBriefVos = groupService.queryJoinedGroup(user.getId());
        return ServerResponse.success(groupBriefVos);
    }

    @ApiOperation(value = "根据群号查找群的所有用户信息")
    @ApiImplicitParam(name = "groupId", value = "群Id", required = true, dataTypeClass = Long.class)
    @PostMapping(value = "/selectGroupUser")
    public ServerResponse<List<GroupUserBriefVo>> queryGroupUsers(@CurrentUser @ApiIgnore User user,Long groupId)
    {
        List<GroupUserBriefVo> groupUserBriefVos = groupService.queryGroupUsers(groupId);
        return ServerResponse.success(groupUserBriefVos);
    }

    @ApiOperation(value = "查询某个群的相关信息")
    @PostMapping(value = "/queryGroupInfo")
    public ServerResponse<GroupVo> queryGroupInfo(@CurrentUser @ApiIgnore User user, Long groupId) throws BusinessException {
        GroupPo groupPo = groupService.queryGroupById(groupId);
        GroupVo groupVo = new GroupVo();
        BeanUtils.copyProperties(groupPo,groupVo);
        Long createTime = groupPo.getCreateTime().getTime();
        groupVo.setCreateTime(createTime);
        return ServerResponse.success(groupVo);
    }

    @ApiOperation(value = "根据群Id修改群相关信息")
    @PostMapping(value = "/updateGroupInfo")
    public ServerResponse<String> updateGroupInfo(@CurrentUser @ApiIgnore User user,GroupUpdateRequest groupUpdateRequest) throws BusinessException {
        groupService.updateGroupInfo(groupUpdateRequest);
        GroupPo groupPo = groupService.queryGroupById(groupUpdateRequest.getId());
        if(groupUpdateRequest.getAvatarUrl() == null){
            groupUpdateRequest.setAvatarUrl(groupPo.getAvatarUrl());
        }
        if(groupUpdateRequest.getName() == null){
            groupUpdateRequest.setName(groupPo.getName());
        }
        executorService.submit(()->{
            try {
                mqProducer.asyncReduceStock(groupUpdateRequest.getId(),groupUpdateRequest.getName(),groupUpdateRequest.getAvatarUrl());
                groupUserRedundantUpdatation.groupUserTableRedundantUpdatate(groupUpdateRequest.getId(),groupUpdateRequest.getName(),groupUpdateRequest.getAvatarUrl());
            } catch (BusinessException e) {
                e.printStackTrace();
            }
        });
        return ServerResponse.success();
    }

    //根据群id添加群成员
    @ApiOperation(value = "根据群id添加群成员", consumes = "application/json", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "添加成功")
    })
    @PostMapping(value = "/addGroupUser", consumes="application/json")
    public ServerResponse<String> addGroupUser(@CurrentUser @ApiIgnore User user, Long groupId,@RequestBody @ApiParam(value = "群初始成员id列表")List<Long> groupUserIds) throws BusinessException {

        if(groupUserIds == null || groupUserIds.size() <= 0 )
        {
            throw new BusinessException(BusinessErrorEnum.PARAMETER_VALIDATION_ERROR);
        }
//        List<GroupUserBriefVo> groupUserBriefVos = groupService.queryGroupUsers(groupId);
//        List<Long> collect = groupUserBriefVos.stream().map(GroupUserBriefVo::getUserid).filter(i -> i.equals(groupUserIds.get(0))).collect(Collectors.toList());
//        if(collect.size() > 0){
//            throw  new BusinessException("群成员已存在");
//        }
        groupService.insertGroupUser(groupId, groupUserIds);
        return ServerResponse.success();
    }


    @ApiOperation(value = "退群")
    @PostMapping(value = "/withdrawFromGroup")
    public ServerResponse<String> withdrawFromGroup(@CurrentUser @ApiIgnore User user,Long groupId) throws BusinessException {
        groupService.withdrawFromGroup(user.getId(), groupId);
        return ServerResponse.success();
    }

}
