package com.hyf.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpStatus;
import com.hyf.demo.entity.*;
import com.hyf.demo.entity.response.SysPermissionResponse;
import com.hyf.demo.exception.BizException;
import com.hyf.demo.service.SysPermissionService;
import com.hyf.demo.service.SysRolePermissionService;
import com.hyf.demo.service.SysUserRoleService;
import com.hyf.demo.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author ikun
 * @Date 2023/8/4 11:24
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Resource
    private SysRolePermissionService sysRolePermissionService;

    @Resource
    private SysPermissionService sysPermissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户
        SysUser sysUser = sysUserService.lambdaQuery()
                .eq(SysUser::getUsername, username)
                .one();
        //为空抛异常
        if (sysUser==null){
            throw new BizException(HttpStatus.HTTP_UNAUTHORIZED,"用户名或密码错误");
        }
        log.info("进来了这里");
        //查根据用户id询权限
        List<SysPermissionResponse> list = queryPermissionByUserId(sysUser.getId());
        //封装成UserDetails对象返回
        return new MyUserDetails(sysUser,list);
    }

    /**
     * 根据用户id查询角色
     * @param userId
     * @return
     */
    private List<SysPermissionResponse> queryPermissionByUserId(Long userId){
        //根据userid查询角色id
        Set<Integer> roleIds = sysUserRoleService.lambdaQuery()
                .eq(SysUserRole::getUserId, userId)
                .eq(SysUserRole::getIsDeleted, 0)
                .list().stream().map(SysUserRole::getRoleId).collect(Collectors.toSet());
        //根据角色id查询权限菜单id
        List<Integer> permissionIds = sysRolePermissionService.lambdaQuery()
                .in(SysRolePermission::getRoleId, roleIds)
                .list()
                .stream()
                .map(SysRolePermission::getPermissionId)
                .collect(Collectors.toList());
        //根据权限id查询权限
        List<SysPermission> sysPermissionList = sysPermissionService.lambdaQuery()
                .in(SysPermission::getId, permissionIds)
                .list();

        //把原来的权限菜单 在转换树的时候，一起重新封装了一份
        List<SysPermissionResponse> treeNode = createTreeNode(1, sysPermissionList);

        //返回树
        return treeNode;
    }

    /**
     * 递归生成树
     * @param parentId
     * @param sysPermissionList
     * @return
     */
    private List<SysPermissionResponse> createTreeNode(Integer parentId , List<SysPermission> sysPermissionList) {
        //创建集合
        List<SysPermissionResponse> list = new ArrayList<>();
        //循环
        for (SysPermission permission : sysPermissionList) {
            //如果当前菜单的id 等于 传入的父级id
            if (permission.getParentId().equals(parentId)){
                //封装拷贝
                SysPermissionResponse sysPermissionResponse = BeanUtil.copyProperties(permission, SysPermissionResponse.class);
                //加入到当前集合
                list.add(sysPermissionResponse);
                //再给当前菜单继续递归查询，是否有子菜单（参数：当前菜单的id和所有菜单集合）
                sysPermissionResponse.setChildren(createTreeNode(Integer.valueOf(permission.getId().toString()),sysPermissionList));
            }
        }
        //返回
        return list;
    }


}
