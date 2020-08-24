package com.im.user.controller;


import com.im.user.annotation.CurrentUser;
import com.im.user.entity.po.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController
{


    @RequestMapping("/user/ddd")
    public String testNeedLogin(@CurrentUser User user)
    {
        return user.toString();
    }

    @RequestMapping("/ssr/ddd")
    public String testNeedLogin1()
    {
        return "joind i11111111111111111111111n1";
    }

    @RequestMapping("/sss/ddd")
    public String testNeedLogin2()
    {
        return "joind in1";
    }

}
