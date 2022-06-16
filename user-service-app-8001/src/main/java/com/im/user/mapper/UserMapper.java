package com.im.user.mapper;

import com.im.user.entity.po.User;
import com.im.user.entity.vo.UserProfileVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserMapper
{
    int deleteByPrimaryKey(Long id);

    int deleteLogicByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(@Param("id")Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int updatePasswordByPrimaryKey(@Param("password")String password, @Param("id")Long id);

    String selectPasswordByUsername(@Param("username")String username);

    User selectUserByUsername(@Param("username") String username);

    List<UserProfileVo> getBatchProfileById(@Param("ids") List<Long> ids);

}