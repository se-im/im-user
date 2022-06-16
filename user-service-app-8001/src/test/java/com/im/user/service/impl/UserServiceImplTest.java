package com.im.user.service.impl;

import com.google.common.collect.Lists;
import com.im.user.entity.po.User;
import com.im.user.entity.vo.UserProfileVo;
import com.mr.response.error.BusinessException;
import com.im.user.service.IUserService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest extends TestCase {


    @Autowired
    private IUserService iUserService;

    @Test
    public void testRegister() throws BusinessException {
        User user = new User();
        user.setUsername("hao");
        user.setPassword("cahjs");

        iUserService.register(user);
    }
    @Test
    public void testgetUserById() throws BusinessException {

//        UserVo userVo = iUserService.getUserById(1L, namespace);
//        System.out.println(userVo);
    }

    @Test
    public void testGetBatchProfile(){
        List<UserProfileVo> batchProfile = iUserService.getBatchProfile(Lists.newArrayList(6L, 8L));
        System.out.println(batchProfile);
    }

}