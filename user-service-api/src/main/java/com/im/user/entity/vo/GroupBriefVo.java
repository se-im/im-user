package com.im.user.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class GroupBriefVo implements Serializable
{
    @ApiModelProperty("群Id")
    private Long groupId;
    @ApiModelProperty("群头像地址")
    private String groupAvatarUrl;
    @ApiModelProperty("群名称")
    private String groupName;

}
