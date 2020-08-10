package com.mr.mapper;

import com.mr.entity.po.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper
{
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(@Param("id")Long id,@Param("namespace") Integer namespace);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int updatePasswordByPrimaryKey(@Param("password")String password, @Param("id")Long id);

    String selectPasswordByUsername(@Param("username")String username,@Param("namespace") Integer namespace);

    User selectUserByUsername(@Param("username") String username, @Param("namespace") Integer namespace);

}