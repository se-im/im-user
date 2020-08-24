package com.mr.entity.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.util.Date;
@Data
@ApiModel("用户注册时的VO对象")
public class UserRegisterVo
{

    @ApiModelProperty("用户名")
    @NonNull
    private String username;

    @ApiModelProperty("密码")
    @NonNull
    private String password;

    @ApiModelProperty("个性签名")
    private String description;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("生日，unix时间戳格式")
    private Long birthday;

    @ApiModelProperty("头像地址")
    private String avatarUrl;

    @ApiModelProperty("性别")
    private String gender;

}
