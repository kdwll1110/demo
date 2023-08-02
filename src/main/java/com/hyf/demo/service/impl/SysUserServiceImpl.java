package com.hyf.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyf.demo.entity.SysUser;
import com.hyf.demo.entity.request.UserRequest;
import com.hyf.demo.service.SysUserService;
import com.hyf.demo.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{

    @Override
    public Integer insertUser(UserRequest userRequest) {
        return null;
    }

    @Override
    public SysUser login(UserRequest userForm) {
        return null;
    }
}




