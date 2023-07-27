package com.hyf.demo.service;

import com.hyf.demo.dto.UserDTO;
import com.hyf.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 胡杨烽
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-07-05 19:38:09
*/
public interface UserService extends IService<User> {

    User login(UserDTO userForm);

    void insertUser(UserDTO userDTO);
}
