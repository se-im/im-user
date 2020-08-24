package com.im.user.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("收到的好友请求vo")
public class ReceivedFriendRequestVo
{

    /**
     * Column:    sender_id
     * Nullable:  false
     */
    private Long senderId;

    /**
     * 0-待验证，1-拒绝，2-同意，3-超时失效
     *
     * Column:    status
     * Nullable:  false
     */
    private Integer status;

    /**
     * 验证备注信息
     *
     * Column:    note
     * Nullable:  false
     */
    private String note;

    /**
     * Column:    sender_username
     * Nullable:  false
     */
    private String senderUsername;

    /**
     * Column:    sender_avatar_url
     * Nullable:  false
     */
    private String senderAvatarUrl;

}
