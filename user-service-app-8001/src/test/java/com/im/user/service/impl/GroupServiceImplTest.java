package com.im.user.service.impl;

import com.im.user.entity.po.GroupPo;
import com.im.user.entity.vo.GroupVo;
import com.im.user.mapper.GroupMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class GroupServiceImplTest {

    @Resource
    private GroupMapper groupMapper;

    Long groupId = 4l;
    @Test
    void queryGroupInfo() {
        GroupPo groupPo = groupMapper.selectByPrimaryKey(groupId);
        GroupVo groupVo = new GroupVo();
        BeanUtils.copyProperties(groupPo,groupVo);
        System.out.println(groupVo+"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

    }
}