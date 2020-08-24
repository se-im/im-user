package com.im.user.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class GroupBriefVo
{
    @ApiModelProperty("群头像地址")
    private String avatartUrl;
    @ApiModelProperty("群名称")
    private String groupName;
    @ApiModelProperty("群成员数目")
    private Integer groupMemberNum;

}
