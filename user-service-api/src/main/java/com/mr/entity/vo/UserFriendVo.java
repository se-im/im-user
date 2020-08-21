package com.mr.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("用户好友的VO对象")
public class UserFriendVo {

    /**
     * Column:    friend_id
     * Nullable:  false
     */
    private Long friendId;

    /**
     * Column:    note
     * Nullable:  false
     */
    private String note;

    /**
     * 用户头像
     *
     * Column:    avatar_url
     * Nullable:  false
     */
    private String avatarUrl;

}
