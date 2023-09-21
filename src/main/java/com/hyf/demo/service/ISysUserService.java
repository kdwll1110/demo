package com.hyf.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyf.demo.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hyf.demo.entity.query.SysUserQuery;
import com.hyf.demo.entity.request.SysUserRequest;
import com.hyf.demo.entity.response.SysUserResponse;
import com.hyf.demo.result.Result;
import com.hyf.demo.util.PageUtil;

import java.util.List;
import java.util.Map;

public interface ISysUserService extends IService<SysUser> {

    Map<String, Object> login(SysUserRequest sysUserRequest);

    Map<String,Object> queryRoleInfoAndPermissionInfo();

    PageUtil queryAllUserByPage(Integer current, Integer size, SysUserQuery query);
}
