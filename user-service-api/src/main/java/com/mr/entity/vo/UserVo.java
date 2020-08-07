package com.mr.entity.vo;

import lombok.Data;

import java.util.Date;
@Data
public class UserVo
{
    private Long id;

    private String username;

    private String description;

    private String email;

    private String phone;

    private Long birthday;

    private String avatarUrl;

    private String role;

    private Long createTime;

    private Long updateTime;

    private Boolean shown;
}
