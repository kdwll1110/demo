package com.hyf.demo.service.impl;

import com.hyf.demo.entity.request.UserRequest;
import com.hyf.demo.service.SysUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class UserServiceImplTest {

    @Resource
    private SysUserService userService;

    @Test
    void login() {
//        UserRequest userRequest = new UserRequest();
//
//        userRequest.setUserPhoneNumber("18593305249");
//
//        User user = userService.login(userRequest);
//
//        Assertions.assertNotNull(user);

    }

    @Test
    void insertUser() {

    }

}