package com.mr.controller;

import com.mr.annotation.CurrentUser;
import com.mr.common.RequestContext;
import com.mr.entity.po.User;
import com.mr.entity.po.UserFriend;
import com.mr.entity.vo.ReceivedFriendQeuestVo;
import com.mr.entity.vo.SendedFriendRequestVo;
import com.mr.entity.vo.UserFriendVo;
import com.mr.entity.vo.UserVo;
import com.mr.response.ServerResponse;
import com.mr.response.error.BusinessException;
import com.mr.service.IUserFriendService;
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

import java.util.List;

@RestController
@RequestMapping("/user/friend/")
@Api(tags = "用户好友相关的api")
@CrossOrigin
public class UserFriendController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IUserFriendService iUserFriendService;

    /**
     * 根据用户名或id查询用户
     * @param user
     * @param query
     * @return
     * @throws BusinessException
     */
    @ApiOperation(value = "根据用户名或id查询用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "query", value = "用户名或id", required = true,dataType = "String"),
    })
    @PostMapping(value = "/detail/id_or_username")
    public ServerResponse<List<UserVo>> queryByIdOrUsername(@CurrentUser User user,String query) throws BusinessException {
        List<UserVo> userVoList = iUserFriendService.fuzzyQuery(query);
        return ServerResponse.success(userVoList);
    }

    /**
     * 发送添加好友请求
     * @param user
     * @param friendUserIdTobeAdded
     * @param note
     * @return
     * @throws BusinessException
     */
    @ApiOperation(value = "发送添加好友请求")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "friendUserIdTobeAdded", value = "请求添加的好友id", required = true,dataType = "Long"),
            @ApiImplicitParam(name = "note", value = "备注信息", required = true,dataType = "String"),
    })
    @PostMapping(value = "/add_friend")
    public ServerResponse<String> addFriend(@CurrentUser User user,Long friendUserIdTobeAdded,String note) throws BusinessException {

            iUserFriendService.addFriend(user,friendUserIdTobeAdded,note);

        return ServerResponse.success("发送好友请求成功！");
    }

    /**
     * 查询收到的好友请求
     * @param user
     * @return
     */
    @ApiOperation(value = "查询收到的好友请求")
    @PostMapping(value = "/queryFriendRequestReceived")
    public ServerResponse<List<ReceivedFriendQeuestVo>> queryFriendRequestReceived(@CurrentUser User user){
       List<ReceivedFriendQeuestVo> receivedFriendQeuestVos =  iUserFriendService.queryFriendRequestReceived(user);
        return ServerResponse.success(receivedFriendQeuestVos);
    }

    /**
     * 查询已发送的好友请求
     * @param user
     * @return
     */
    @ApiOperation(value = "查询已发送的好友请求")
    @PostMapping(value = "/queryFriendRequestSended")
    public ServerResponse<List<SendedFriendRequestVo>> queryFriendRequestSended(@CurrentUser User user){
        List<SendedFriendRequestVo> sendedFriendRequestVos =  iUserFriendService.queryFriendRequestSended(user);
        return ServerResponse.success(sendedFriendRequestVos);
    }

    /**
     * 处理收到的好友请求
     * @param user
     * @param requestId
     * @param status
     * @return
     * @throws BusinessException
     */

    @ApiOperation(value = "处理收到的好友请求")
    @PostMapping(value = "/processFriendRequest")
    public ServerResponse<String> processFriendRequest(@CurrentUser User user,Long requestId,Integer status) throws BusinessException {
        iUserFriendService.processMyFriendRequest(user,requestId,status);
        return ServerResponse.success("同意好友请求！");
    }

    /**
     * 查询当前用户的好友
     * @param user
     * @return
     */
    @ApiOperation(value = "查询当前用户的好友")
    @PostMapping(value = "/queryFriend")
    public ServerResponse<List<UserFriendVo>> queryFriend(@CurrentUser User user){
        List<UserFriendVo> userFriendVos = iUserFriendService.queryMyFriend(user);
        return ServerResponse.success(userFriendVos);
    }

}
