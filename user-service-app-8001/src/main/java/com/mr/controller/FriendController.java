package com.mr.controller;

import com.mr.annotation.CurrentUser;
import com.mr.entity.po.User;
import com.mr.entity.vo.ReceivedFriendQeuestVo;
import com.mr.entity.vo.SendedFriendRequestVo;
import com.mr.entity.vo.FriendUserVo;
import com.mr.entity.vo.UserVo;
import com.mr.response.ServerResponse;
import com.mr.response.error.BusinessException;
import com.mr.service.IFriendService;
import com.mr.service.IUserService;
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



    @ApiOperation(value = "根据用户名或id查询用户信息")
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
    public ServerResponse<String> addFriend(@CurrentUser @ApiIgnore User user,Long friendUserIdTobeAdded,String note) throws BusinessException {

        //TODO 已是好友校验

        FriendUserVo friendUserVo = iFriendService.queryFriendDetail(user, friendUserIdTobeAdded);
        if(friendUserVo != null){
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
    public ServerResponse<List<ReceivedFriendQeuestVo>> queryFriendRequestReceived(@CurrentUser @ApiIgnore User user){
       List<ReceivedFriendQeuestVo> receivedFriendQeuestVos =  iFriendService.queryFriendRequestReceived(user);
        return ServerResponse.success(receivedFriendQeuestVos);
    }

    /**
     * 查询已发送的好友请求
     */
    @ApiOperation(value = "查询已发送的好友请求")
    @PostMapping(value = "/queryFriendRequestSended")
    public ServerResponse<List<SendedFriendRequestVo>> queryFriendRequestSended(@CurrentUser @ApiIgnore User user){
        List<SendedFriendRequestVo> sendedFriendRequestVos =  iFriendService.queryFriendRequestSended(user);
        return ServerResponse.success(sendedFriendRequestVos);
    }

    /**
     * 查询已发送的某个好友请求
     * @return
     */
    @ApiOperation(value = "查询已发送的某个好友请求")
    @PostMapping(value = "/queryFriendRequestSendedDetail")
    public ServerResponse<SendedFriendRequestVo> queryFriendRequestSendedDetail(@CurrentUser @ApiIgnore User user,Long friendUserIdTobeAdded){
        SendedFriendRequestVo sendedFriendRequestVo =  iFriendService.queryFriendRequestSendedDetail(user,friendUserIdTobeAdded);
        return ServerResponse.success(sendedFriendRequestVo);
    }

    /**
     * 处理收到的好友请求
     */

    @ApiOperation(value = "处理收到的好友请求")
    @PostMapping(value = "/processFriendRequest")
    public ServerResponse<String> processFriendRequest(@CurrentUser @ApiIgnore User user,Long requestId,Integer status) throws BusinessException {
        iFriendService.processMyFriendRequest(user,requestId,status);
        return ServerResponse.success("同意好友请求！");
    }

    /**
     * 查询当前用户的好友
     */
    @ApiOperation(value = "查询当前用户的好友")
    @PostMapping(value = "/queryFriend")
    public ServerResponse<List<FriendUserVo>> queryFriend(@CurrentUser @ApiIgnore User user){
        List<FriendUserVo> friendUserVos = iFriendService.queryMyFriend(user);
        return ServerResponse.success(friendUserVos);
    }

    /**
     * 查询当前用户的某个好友
     * @param user
     * @param friendId
     * @return
     */
    @ApiOperation(value = "查询当前用户的某个好友")
    @PostMapping(value = "/queryFriendDetail")
    public ServerResponse<FriendUserVo> queryFriendDetail(@CurrentUser @ApiIgnore User user, Long friendId){
        FriendUserVo friendUserVo = iFriendService.queryFriendDetail(user,friendId);
        return ServerResponse.success(friendUserVo);
    }
    /**
     * 删除当前用户的某个好友
     */
    @ApiOperation(value = "删除当前用户的某个好友")
    @PostMapping(value = "/deleteFriend")
    public ServerResponse<String> deleteFriend(@CurrentUser @ApiIgnore User user,Long friendId) throws BusinessException {
        iFriendService.deleteFriend(user,friendId);
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