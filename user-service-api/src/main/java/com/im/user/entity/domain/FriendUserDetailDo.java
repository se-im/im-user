package com.im.user.entity.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("用户好友的详细VO对象")
public class FriendUserDetailDo {
    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("备注")
    private String note;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户简介")
    private String description;

    @ApiModelProperty("用户邮箱")
    private String email;

    @ApiModelProperty("用户电话")
    private String phone;

    @ApiModelProperty("生日，返回的格式为unix时间戳")
    private Date birthday;

    @ApiModelProperty("用户头像图片url")
    private String avatarUrl;

    @ApiModelProperty("性别")
    private Byte gender;
}
