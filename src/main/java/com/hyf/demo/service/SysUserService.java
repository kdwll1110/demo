package com.hyf.demo.service;

import com.hyf.demo.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hyf.demo.entity.request.SysUserRequest;
import com.hyf.demo.entity.response.SysUserResponse;

public interface SysUserService extends IService<SysUser> {

    SysUserResponse login(SysUserRequest sysUserRequest);
}
