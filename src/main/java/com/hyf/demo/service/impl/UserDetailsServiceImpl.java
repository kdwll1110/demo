package com.hyf.demo.service.impl;

import cn.hutool.http.HttpStatus;
import com.hyf.demo.entity.*;
import com.hyf.demo.entity.response.SysMenuResponse;
import com.hyf.demo.entity.response.SysRoleResponse;
import com.hyf.demo.exception.BizException;
import com.hyf.demo.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    private SysMenuService sysMenuService;

    @Resource
    private SysRoleService sysRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户
        SysUser sysUser = sysUserService.lambdaQuery()
                .eq(SysUser::getUsername, username)
                .one();
        //为空抛异常
        if (sysUser == null) {
            throw new BizException(HttpStatus.HTTP_UNAUTHORIZED, "用户名或密码错误");
        }
        log.info("进来了这里");
        //查询用户角色
        List<SysRoleResponse> sysRoleResponses = sysRoleService.queryRoleByUserId(sysUser.getId());
        //查根据用户id询权限
        List<SysMenuResponse> sysMenuRespons = sysMenuService.queryPermissionByUserId(sysUser.getId());
        //封装成UserDetails对象返回
        return new MyUserDetails(sysUser, sysRoleResponses, sysMenuRespons);
    }





}
