package com.im.user.entity.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Table: im_group
 */
@Data
@Builder

public class GroupPo implements Serializable {
    /**
     * Column:    id
     * Nullable:  false
     */
    private Long id;

    /**
     * 群聊名称
     *
     * Column:    name
     * Nullable:  false
     */
    private String name;

    /**
     * 群聊头像图片地址
     *
     * Column:    avatar_url
     * Nullable:  false
     */
    private String avatarUrl;

    /**
     *  群聊类型；保留字段
     *
     * Column:    group_type
     * Nullable:  false
     */
    private Integer groupType;

    /**
     * 群成员人数
     *
     * Column:    member_num
     * Nullable:  false
     */
    private Integer memberNum;

    /**
     * 创建者id
     *
     * Column:    create_user_id
     * Nullable:  true
     */
    private Long createUserId;

    /**
     * 群描述
     *
     * Column:    description
     * Nullable:  false
     */
    private String description;

    /**
     * 创建时间
     *
     * Column:    create_time
     * Nullable:  false
     */
    private Date createTime;

    /**
     * 最后更新时间
     *
     * Column:    update_time
     * Nullable:  true
     */
    private Date updateTime;
}