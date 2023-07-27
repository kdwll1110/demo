package com.hyf.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyf.demo.annotation.OperationType;
import com.hyf.demo.dto.UserDTO;
import com.hyf.demo.entity.User;
import com.hyf.demo.exception.BizException;
import com.hyf.demo.service.UserService;
import com.hyf.demo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Supplier;

/**
* @author 胡杨烽
* @description 针对表【user(用户表)】的数据库操作Service实现
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @OperationType(action = "登录功能")
    @Override
    public User login(UserDTO user) {

        User dataUser = lambdaQuery().eq(User::getUserPhoneNumber, user.getUserPhoneNumber()).one();

        Optional.ofNullable(dataUser).orElseThrow(()-> new BizException(HttpStatus.HTTP_UNAUTHORIZED,"用户名或密码错误"));

        return dataUser;
    }

    @OperationType(action = "新增用户功能")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertUser(UserDTO userDTO) {

        Optional.ofNullable(userDTO).orElseThrow(()-> new BizException(HttpStatus.HTTP_BAD_REQUEST,"新增失败，参数为空"));

        User user = BeanUtil.copyProperties(userDTO, User.class);

        try {
            save(user);
        } catch (Exception e) {
            throw new BizException(HttpStatus.HTTP_BAD_REQUEST,"账号已经存在！");
        }

    }


}




