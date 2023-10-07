package com.hyf.demo.service;

import com.hyf.demo.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hyf.demo.entity.query.SysRoleQuery;
import com.hyf.demo.entity.request.SysRoleRequest;
import com.hyf.demo.entity.response.SysRoleResponse;
import com.hyf.demo.result.Result;
import com.hyf.demo.util.PageUtil;

import java.util.List;
import java.util.Set;

public interface ISysRoleService extends IService<SysRole> {

    Set<Integer> queryRoleIds(Long userId);

    List<SysRoleResponse> queryRoleByUserId(Long userId);

    List<SysRoleResponse> queryAllRole();

    PageUtil queryAllRoleByPage(Integer current, Integer size, SysRoleQuery query);

    SysRoleResponse queryRoleById(Integer roleId);

    Result addRole(SysRoleRequest request);

    Result updateRole(SysRoleRequest request);

    Result deleteRole(List<Integer> roleIds);
}
