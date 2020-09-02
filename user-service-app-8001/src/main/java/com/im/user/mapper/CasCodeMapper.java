package com.im.user.mapper;

import com.im.user.entity.po.CasCode;

public interface CasCodeMapper
{
    int deleteByPrimaryKey(Integer id);

    int insert(CasCode record);

    int insertSelective(CasCode record);

    CasCode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CasCode record);

    int updateByPrimaryKey(CasCode record);
}