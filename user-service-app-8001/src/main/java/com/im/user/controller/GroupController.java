package com.im.user.controller;


import com.im.user.annotation.CurrentUser;
import com.im.user.entity.po.User;
import com.im.user.entity.vo.GroupBriefVo;
import com.im.user.entity.vo.GroupUserBriefVo;
import com.im.user.exception.BusinessErrorEnum;
import com.im.user.service.IGroupService;
import com.mr.response.ServerResponse;
import com.mr.response.error.BusinessException;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/user/group/")
@Api(tags = "群相关的api")
@CrossOrigin
public class GroupController
{

    @Autowired
    private IGroupService groupService;

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


    @GetMapping("/joined")
    public List<GroupBriefVo> queryJoinedGroup(@CurrentUser @ApiIgnore User user)
    {

        return null;
    }


    //修改群相关信息

    //

    public List<GroupUserBriefVo> queryGroupUser(Long groupId)
    {
        return null;
    }
}
