package com.mr.entity.po;

import lombok.Data;

import java.util.Date;

/**
 * Table: user_friend_request
 */
@Data
public class AddFriendRequest {
    /**
     * Column:    id
     * Nullable:  false
     */
    private Long id;

    /**
     * Column:    sender_id
     * Nullable:  false
     */
    private Long senderId;

    /**
     * Column:    receiver_id
     * Nullable:  false
     */
    private Long receiverId;

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

    /**
     * Column:    receiver_username
     * Nullable:  false
     */
    private String receiverUsername;

    /**
     * Column:    receiver_avatar_url
     * Nullable:  false
     */
    private String receiverAvatarUrl;

    /**
     * Column:    create_time
     * Nullable:  false
     */
    private Date createTime;

    /**
     * Column:    update_time
     * Nullable:  false
     */
    private Date updateTime;
}