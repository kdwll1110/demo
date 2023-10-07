package com.hyf.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyf.demo.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hyf.demo.entity.query.SysUserQuery;
import com.hyf.demo.entity.request.LoginForm;
import com.hyf.demo.entity.request.SysUserRequest;
import com.hyf.demo.entity.response.SysUserResponse;
import com.hyf.demo.result.Result;
import com.hyf.demo.util.PageUtil;

import java.util.List;
import java.util.Map;

public interface ISysUserService extends IService<SysUser> {

    Map<String, Object> login(LoginForm loginForm);

    Map<String,Object> queryRoleInfoAndPermissionInfo();

    PageUtil queryAllUserByPage(Integer current, Integer size, SysUserQuery query);

    SysUserResponse queryUserById(Long userId);

    Result updateUser(SysUserRequest sysUserRequest);

    Result addUser(SysUserRequest sysUserRequest);

    Result deleteUser(List<Integer> userIds);
}
