package com.im.user.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("群聊修改请求对象")
public class GroupUpdateRequest {

    /**
     * 群Id
     *
     * Column:    name
     * Nullable:  false
     */
    @ApiModelProperty("群Id")
    private Long id;

    /**
     * 群聊名称
     *
     * Column:    name
     * Nullable:  false
     */
    @ApiModelProperty("修改后的群聊名称")
    private String name;

    /**
     * 群聊头像图片地址
     *
     * Column:    avatar_url
     * Nullable:  false
     */
    @ApiModelProperty("群头像")
    private String avatarUrl;

    /**
     * 群描述
     *
     * Column:    description
     * Nullable:  false
     */
    @ApiModelProperty("群描述")
    private String description;

}
