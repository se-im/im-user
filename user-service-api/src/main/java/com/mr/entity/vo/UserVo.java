package com.mr.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
@ApiModel("用户vo")
public class UserVo
{
    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户简介")
    private String description;

    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("用户电话")
    private String phone;

    @ApiModelProperty("生日，返回的格式为unix时间戳")
    private Long birthday;

    @ApiModelProperty("用户头像图片url")
    private String avatarUrl;

    @ApiModelProperty("用户创建时间， unix时间戳格式")
    private Long createTime;

    @ApiModelProperty("是否对陌生人可见")
    private Boolean shown;
}
