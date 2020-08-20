package com.mr.entity.po;

import java.util.Date;
import lombok.Data;

/**
 * Table: user_friend
 */
@Data
public class UserFriend {
    /**
     * Column:    id
     * Nullable:  false
     */
    private Long id;

    /**
     * Column:    user_id
     * Nullable:  false
     */
    private Long userId;

    /**
     * Column:    friend_id
     * Nullable:  false
     */
    private Long friendId;

    /**
     * Column:    note
     * Nullable:  false
     */
    private String note;

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

    /**
     * 0-正常，1-被删除
     *
     * Column:    deleted
     * Nullable:  false
     */
    private Byte deleted;
}