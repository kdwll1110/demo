package com.hyf.demo.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollUtil;
import com.hyf.demo.entity.SysPermission;
import com.hyf.demo.entity.SysRole;
import com.hyf.demo.entity.SysRolePermission;
import com.hyf.demo.entity.SysUserRole;
import com.hyf.demo.service.SysPermissionService;
import com.hyf.demo.service.SysRolePermissionService;
import com.hyf.demo.service.SysRoleService;
import com.hyf.demo.service.SysUserRoleService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author ikun
 * @Date 2023/8/3 9:03
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Resource
    private SysRolePermissionService sysRolePermissionService;

    @Resource
    private SysPermissionService sysPermissionService;

    @Resource
    private SysRoleService sysRoleService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {

        //根据用户id获取 用户角色中间表的信息
        List<SysUserRole> userRoleList = sysUserRoleService.lambdaQuery()
                .eq(SysUserRole::getUserId, Integer.valueOf(loginId.toString()))
                .list();
        //将集合遍历 转换成 角色id集合
        Set<Integer> roleIds = userRoleList.stream()
                .map(SysUserRole::getRoleId)
                .collect(Collectors.toSet());
        //根据角色id集合 去 查询角色权限中间表的信息
        List<SysRolePermission> rolePermissionList = sysRolePermissionService.lambdaQuery()
                .in(SysRolePermission::getRoleId, roleIds)
                .list();
        //将集合遍历 转换成 权限id集合
        List<Integer> permissionIds = rolePermissionList.stream()
                .map(SysRolePermission::getPermissionId)
                .collect(Collectors.toList());

        List<String> collect = new ArrayList<>();
        if (CollUtil.isNotEmpty(permissionIds)){
            //根据权限id 去查询所拥有的权限
            List<SysPermission> permissionList = sysPermissionService.lambdaQuery()
                    .in(SysPermission::getId, permissionIds)
                    .list();
            //获取权限名称
           collect = permissionList.stream()
                    .map(SysPermission::getName)
                    .collect(Collectors.toList());
        }

        return collect;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        //根据用户id获取 用户角色中间表的信息
        List<SysUserRole> userRoleList = sysUserRoleService.lambdaQuery()
                .eq(SysUserRole::getUserId, Integer.valueOf(loginId.toString()))
                .list();
        //将集合遍历 转换成 角色id集合
        Set<Integer> roleIds = userRoleList.stream()
                .map(SysUserRole::getRoleId)
                .collect(Collectors.toSet());
        
        List<String> roleList = sysRoleService.lambdaQuery()
                .in(SysRole::getId, roleIds)
                .list()
                .stream()
                .map(SysRole::getName)
                .collect(Collectors.toList());
        return roleList;
    }
}
