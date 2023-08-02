package com.hyf.demo.service;

import com.hyf.demo.entity.request.UserRequest;
import com.hyf.demo.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hyf.demo.util.Result;

public interface SysUserService extends IService<SysUser> {

    Integer insertUser(UserRequest userRequest);

    SysUser login(UserRequest userForm);
}
