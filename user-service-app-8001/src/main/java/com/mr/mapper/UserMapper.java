package com.mr.mapper;

import com.mr.entity.po.User;

public interface UserMapper
{
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    String selectPasswordByUsername(String username);

    User selectUserByUsername(String username);

}