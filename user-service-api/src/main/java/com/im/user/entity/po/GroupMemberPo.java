package com.im.user.entity.po;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Table: im_group_user
 */
@Data
@Builder
public class GroupMemberPo  implements Serializable {
    /**
     * Column:    id
     * Nullable:  false
     */
    private Long id;

    /**
     * 群聊id
     *
     * Column:    group_id
     * Nullable:  false
     */
    private Long groupId;

    /**
     * 群聊名称
     *
     * Column:    group_name
     * Nullable:  false
     */
    private String groupName;

    /**
     * 群头像地址
     *
     * Column:    group_avatar_url
     * Nullable:  false
     */
    private String groupAvatarUrl;

    /**
     * 群成员id
     *
     * Column:    user_id
     * Nullable:  false
     */
    private Long userId;

    /**
     * 群成员姓名
     *
     * Column:    user_name
     * Nullable:  false
     */
    private String userName;

    /**
     * 群成员头像
     *
     * Column:    user_avatar_url
     * Nullable:  false
     */
    private String userAvatarUrl;

    /**
     * 群身份：0-普通成员；1-管理员；2-群主
     *
     * Column:    user_role
     * Nullable:  false
     */
    private Integer userRole;

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
     * 是否退群：0-没有；1-已退群
     *
     * Column:    deleted
     * Nullable:  false
     */
    private Integer deleted;
}