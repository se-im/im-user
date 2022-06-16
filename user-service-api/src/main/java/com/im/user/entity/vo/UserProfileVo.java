package com.im.user.entity.vo;

import lombok.Data;

/**
 * 用户的profile，用于显示头像，昵称
 */
@Data
public class UserProfileVo {
    private Long userId;
    private String nick;
    private String avatarUrl;
}
