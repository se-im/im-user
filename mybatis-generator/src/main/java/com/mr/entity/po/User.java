package com.mr.entity.po;

import java.util.Date;
import lombok.Data;

/**
 * Table: im_user
 */
@Data
public class User {
    /**
     * 用户表id
     *
     * Column:    id
     * Nullable:  false
     */
    private Long id;

    /**
     * 用户名
     *
     * Column:    username
     * Nullable:  false
     */
    private String username;

    /**
     * 用户密码，MD5加密
     *
     * Column:    password
     * Nullable:  false
     */
    private String password;

    /**
     * 个人简介
     *
     * Column:    description
     * Nullable:  false
     */
    private String description;

    /**
     * 邮箱
     *
     * Column:    email
     * Nullable:  false
     */
    private String email;

    /**
     * 电话
     *
     * Column:    phone
     * Nullable:  false
     */
    private String phone;

    /**
     * 生日
     *
     * Column:    birthday
     * Nullable:  true
     */
    private Date birthday;

    /**
     * 用户头像
     *
     * Column:    avatar_url
     * Nullable:  false
     */
    private String avatarUrl;

    /**
     * 角色0-员工,1-管理员， 2-会议室管理员
     *
     * Column:    role
     * Nullable:  false
     */
    private Integer role;

    /**
     * 创建时间
     *
     * Column:    create_time
     * Nullable:  false
     */
    private Date createTime;

    /**
     * 最后一次更新时间
     *
     * Column:    update_time
     * Nullable:  false
     */
    private Date updateTime;

    /**
     * 是否删除，0-没有, 1-已删除
     *
     * Column:    deleted
     * Nullable:  false
     */
    private Byte deleted;

    /**
     * 性别：0 男 1 女
     *
     * Column:    gender
     * Nullable:  false
     */
    private Byte gender;

    /**
     * 陌生人是否可以看到 0-可以  1-不可以
     *
     * Column:    shown
     * Nullable:  false
     */
    private Byte shown;
}