package com.im.user.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class GroupVo implements Serializable
{

    private Long id;

    /**
     * 群聊名称
     */
    private String name;

    /**
     * 群聊头像图片地址
     */
    private String avatarUrl;


    /**
     * 群成员人数
     */
    private Integer memberNum;

    /**
     * 创建者id
     */
    private Long createUserId;

    /**
     * 群描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Long createTime;

}
