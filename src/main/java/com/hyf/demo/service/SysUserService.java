package com.hyf.demo.service;

import com.hyf.demo.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hyf.demo.entity.request.SysUserRequest;

import java.util.Map;

public interface SysUserService extends IService<SysUser> {

    Map<String, Object> login(SysUserRequest sysUserRequest);
}
