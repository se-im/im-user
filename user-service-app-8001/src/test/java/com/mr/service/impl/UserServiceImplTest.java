package com.mr.service.impl;

import com.mr.entity.po.User;
import com.mr.entity.vo.UserVo;
import com.mr.response.error.BusinessException;
import com.mr.service.IUserService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
        user.setNamespace(1);

        iUserService.register(user);
    }
    @Test
    public void testgetUserById() throws BusinessException {

        UserVo userVo = iUserService.getUserById(1L);
        System.out.println(userVo);
    }

}