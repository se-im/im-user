package com.im.user.controller;

import com.im.user.annotation.CurrentUser;
import com.im.user.entity.po.User;
import com.im.user.entity.vo.*;
import com.mr.response.ServerResponse;
import com.mr.response.error.BusinessException;
import com.im.user.service.IFriendService;
import com.im.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/user/friend/")
@Api(tags = "用户好友相关的api")
@CrossOrigin
public class FriendController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IFriendService iFriendService;



    @ApiOperation(value = "根据用户名或id查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "query", value = "用户名或id", required = true,dataType = "String"),
    })
    @PostMapping(value = "/detail/id_or_username")
    public ServerResponse<List<UserVo>> queryByIdOrUsername(@CurrentUser @ApiIgnore User user, String query) throws BusinessException {
        List<UserVo> userVoList = iFriendService.fuzzyQuery(query);
        return ServerResponse.success(userVoList);
    }


    @ApiOperation(value = "发送添加好友请求")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "friendUserIdTobeAdded", value = "请求添加的好友id", required = true,dataType = "Long"),
            @ApiImplicitParam(name = "note", value = "备注信息", required = true,dataType = "String"),
    })
    @PostMapping(value = "/add_friend")
    public ServerResponse<String> addFriend(@CurrentUser @ApiIgnore User user, Long friendUserIdTobeAdded, String note) throws BusinessException {

        //TODO 已是好友校验

        FriendUserBriefVo friendUserBriefVo = iFriendService.queryFriendBrief(user, friendUserIdTobeAdded);
        if(friendUserBriefVo != null){
            throw new BusinessException("你们已是好友！");
        }
        //TODO 重复发送
        SendedFriendRequestVo sendedFriendRequestVo = iFriendService.queryFriendRequestSendedDetail(user, friendUserIdTobeAdded);
        if(sendedFriendRequestVo == null){
            iFriendService.addFriend(user,friendUserIdTobeAdded,note);
        }

        return ServerResponse.success("发送好友请求成功！");
    }


    @ApiOperation(value = "查询收到的好友请求")

    @PostMapping(value = "/queryFriendRequestReceived")
    public ServerResponse<List<ReceivedFriendRequestVo>> queryFriendRequestReceived(@CurrentUser @ApiIgnore User user){
       List<ReceivedFriendRequestVo> ReceivedFriendRequestVos =  iFriendService.queryFriendRequestReceived(user);
        return ServerResponse.success(ReceivedFriendRequestVos);
    }

    @ApiOperation(value = "查询已发送的好友请求")
    @PostMapping(value = "/queryFriendRequestSended")
    public ServerResponse<List<SendedFriendRequestVo>> queryFriendRequestSended(@CurrentUser @ApiIgnore User user){
        List<SendedFriendRequestVo> sendedFriendRequestVos =  iFriendService.queryFriendRequestSended(user);
        return ServerResponse.success(sendedFriendRequestVos);
    }

    @ApiOperation(value = "查询已发送的某个好友请求")
    @PostMapping(value = "/queryFriendRequestSendedDetail")
    public ServerResponse<SendedFriendRequestVo> queryFriendRequestSendedDetail(@CurrentUser @ApiIgnore User user, Long friendUserIdTobeAdded){
        SendedFriendRequestVo sendedFriendRequestVo =  iFriendService.queryFriendRequestSendedDetail(user,friendUserIdTobeAdded);
        return ServerResponse.success(sendedFriendRequestVo);
    }

    @ApiOperation(value = "处理收到的好友请求")
    //TODO
    @ApiImplicitParams({
            /**
             *
             */
            @ApiImplicitParam(name = "requestId", value = "某条好友请求的Id", required = true,dataType = "Long"),
            @ApiImplicitParam(name = "status", value = "某条好友请求状态：1-拒绝 2-同意", required = true,dataType = "Integer")
    })
    @PostMapping(value = "/processFriendRequest")
    public ServerResponse<String> processFriendRequest(@CurrentUser @ApiIgnore User user, Long requestId, Integer status) throws BusinessException {
        iFriendService.processMyFriendRequest(user,requestId,status);
        return ServerResponse.success("同意好友请求！");
    }

    @ApiOperation(value = "查询当前用户的好友")
    @PostMapping(value = "/queryFriend")
    public ServerResponse<List<FriendUserBriefVo>> queryFriend(@CurrentUser @ApiIgnore User user) throws BusinessException {
        List<FriendUserBriefVo> friendUserBriefVos = iFriendService.queryMyFriend(user);

        for(FriendUserBriefVo friendUserBriefVo:friendUserBriefVos){
            if(friendUserBriefVo.getNote().equals("")){
                User userById = iUserService.getUserById(friendUserBriefVo.getFriendId());
                friendUserBriefVo.setNote(userById.getUsername());
            }
        }
        return ServerResponse.success(friendUserBriefVos);
    }

    @ApiOperation(value = "查询当前用户的某个好友(搜索好友的时候用)")
    @ApiImplicitParams({
            /**
             *
             */
            @ApiImplicitParam(name = "friendId", value = "好友的Id", required = true,dataType = "Long")
    })
    @PostMapping(value = "/queryFriendBrief")
    public ServerResponse<FriendUserBriefVo> queryFriendBrief(@CurrentUser @ApiIgnore User user, Long friendId) throws BusinessException {
        FriendUserBriefVo friendUserBriefVo = iFriendService.queryFriendBrief(user,friendId);
        return ServerResponse.success(friendUserBriefVo);
    }

    @ApiOperation(value = "查询当前用户的某个好友(Profile面板展示用)")
    @ApiImplicitParams({
            /**
             *
             */
            @ApiImplicitParam(name = "friendId", value = "好友的Id", required = true,dataType = "Long")
    })
    @PostMapping(value = "/queryFriendDetail")
    public ServerResponse<FriendUserDetailVo> queryFriendDetail(@CurrentUser @ApiIgnore User user, Long friendId) throws BusinessException {
        FriendUserDetailVo friendUserDetailVo = iFriendService.queryFriendDetail(user.getId(), friendId);
        return ServerResponse.success(friendUserDetailVo);
    }

    @ApiOperation(value = "删除当前用户的某个好友")
    @ApiImplicitParams({
            /**
             *
             */
            @ApiImplicitParam(name = "friendId", value = "好友的Id", required = true,dataType = "Long")
    })
    @PostMapping(value = "/deleteFriend")
    public ServerResponse<String> deleteFriend(@CurrentUser @ApiIgnore User user, Long friendId) throws BusinessException {
        iFriendService.deleteFriend(user,friendId);
        return ServerResponse.success();
    }

    //TODO 给好友修改备注
    @ApiOperation(value = "给好友修改备注")
    @ApiImplicitParams({
            /**
             *
             */
            @ApiImplicitParam(name = "friendId", value = "好友的Id", required = true,dataType = "Long"),
            @ApiImplicitParam(name = "note", value = "备注", required = true,dataType = "String")
    })
    @PostMapping(value = "/updateFriendNote")
    public ServerResponse<String> updateFriendNote(@CurrentUser @ApiIgnore User user, Long friendId,String note) throws BusinessException {
        iFriendService.updateFriendNote(user.getId(),friendId,note);
        return ServerResponse.success();
    }



    @ApiOperation(value = "直接暴力添加好友")
    @PostMapping(value = "/violenceAddFriend")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "friendId", value = "好友的id", required = true,dataType = "Long"),
    })
    public ServerResponse<String> addFriendAdmin(@CurrentUser @ApiIgnore User user, Long friendId) throws BusinessException
    {
        iFriendService.violenceAddFriend(user.getId(), friendId);
        return ServerResponse.success();
    }

}
