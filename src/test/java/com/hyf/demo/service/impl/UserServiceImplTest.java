package com.hyf.demo.service.impl;

import com.hyf.demo.dto.UserDTO;
import com.hyf.demo.entity.User;
import com.hyf.demo.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Resource
    private UserService userService;

    @Test
    void login() {
        UserDTO userDTO = new UserDTO();

        userDTO.setUserPhoneNumber("18593305249");

        User user = userService.login(userDTO);

        Assertions.assertNotNull(user);

    }

    @Test
    void insertUser() {

    }

}