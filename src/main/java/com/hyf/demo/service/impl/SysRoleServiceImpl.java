package com.hyf.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyf.demo.entity.SysRole;
import com.hyf.demo.entity.SysUser;
import com.hyf.demo.entity.SysUserRole;
import com.hyf.demo.entity.query.SysRoleQuery;
import com.hyf.demo.entity.request.SysRoleRequest;
import com.hyf.demo.entity.response.SysRoleResponse;
import com.hyf.demo.exception.BizException;
import com.hyf.demo.result.Result;
import com.hyf.demo.service.ISysRoleService;
import com.hyf.demo.mapper.SysRoleMapper;
import com.hyf.demo.service.ISysUserRoleService;
import com.hyf.demo.util.PageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
        implements ISysRoleService {

    @Resource
    private ISysUserRoleService ISysUserRoleService;

    /**
     * 根据传入的用户id查询中间表的角色id集合
     *
     * @param userId
     * @return
     */
    public Set<Integer> queryRoleIds(Long userId){
        //根据userid查询角色id
        Set<Integer> roleIds = ISysUserRoleService.lambdaQuery()
                .eq(SysUserRole::getUserId, userId)
                .eq(SysUserRole::getIsDeleted, 0)
                .list().stream().map(SysUserRole::getRoleId).collect(Collectors.toSet());
        //此处是编写修改用户信息时注释的
//        if (CollUtil.isEmpty(roleIds)) {
//            throw new BizException(HttpStatus.HTTP_UNAUTHORIZED, "当前用户尚未绑定角色");
//        }
        return roleIds;
    }

    /**
     * 返回角色信息
     *
     * @param userId
     * @return
     */
    public List<SysRoleResponse> queryRoleByUserId(Long userId) {
        Set<Integer> roleIds = queryRoleIds(userId);
        System.out.println("roleIds = " + roleIds);
        List<SysRole> sysRoles = null;
        try {
            sysRoles = lambdaQuery().in(SysRole::getId, roleIds).list();
        } catch (Exception e) {
            throw new BizException(HttpStatus.HTTP_UNAUTHORIZED,"当前账号尚未绑定角色，无法登录");
        }

        return BeanUtil.copyToList(sysRoles, SysRoleResponse.class);
    }

    @Override
    public List<SysRoleResponse> queryAllRole() {
        List<SysRole> list = lambdaQuery().list();
        List<SysRoleResponse> sysRoleResponses = BeanUtil.copyToList(list, SysRoleResponse.class);
        return sysRoleResponses;
    }

    @Override
    public PageUtil queryAllRoleByPage(Integer current, Integer size, SysRoleQuery query) {

        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();

        if (query != null) {
            if (StrUtil.isNotBlank(query.getName())) {
                queryWrapper.like(SysRole::getName, query.getName());
            }
        }

        Page<SysRole> page = baseMapper.selectPage(new Page<>(current, size), queryWrapper);

        List<SysRoleResponse> list = BeanUtil.copyToList(page.getRecords(), SysRoleResponse.class);

        return new PageUtil(page.getCurrent(), page.getSize(), page.getTotal(), list);
    }

    @Override
    public SysRoleResponse queryRoleById(Integer roleId) {
        SysRole sysRole = baseMapper.selectById(roleId);
        if (sysRole == null) {
            throw new BizException(HttpStatus.HTTP_BAD_REQUEST, "角色不存在");
        }
        return BeanUtil.copyProperties(sysRole, SysRoleResponse.class);

    }

    @Override
    public Result addRole(SysRoleRequest request) {
        if (request == null) {
            throw new BizException(HttpStatus.HTTP_BAD_REQUEST, "参数不能为空");
        }
        SysRole sysRole = BeanUtil.copyProperties(request, SysRole.class);
        save(sysRole);
        return Result.success("操作成功", null);
    }

    @Override
    public Result updateRole(SysRoleRequest request) {
        SysRole sysRole = lambdaQuery().eq(SysRole::getId, request.getId()).one();
        if (sysRole == null) {
            throw new BizException(HttpStatus.HTTP_BAD_REQUEST, "角色不存在");
        }
        updateById(BeanUtil.copyProperties(request, SysRole.class));
        return Result.success("操作成功", null);
    }

    @Override
    public Result deleteRole(List<Integer> roleIds) {
        roleIds.forEach(System.out::println);
        if (CollUtil.isEmpty(roleIds)) {
            throw new BizException(HttpStatus.HTTP_BAD_REQUEST, "请选择要删除的角色");
        }
        Set<Integer> userIds = ISysUserRoleService.lambdaQuery()
                .in(SysUserRole::getRoleId, roleIds)
                .list()
                .stream()
                .map(SysUserRole::getUserId)
                .collect(Collectors.toSet());

//        lambdaQuery().in(SysRole::getId, roleIds).list().stream().forEach(sysRole -> {
//            if ("SUPER_ADMIN".equals(sysRole.getRoleKey())) {
//                throw new BizException(HttpStatus.HTTP_BAD_REQUEST, "超级管理员禁止删除");
//            }
//        });

        if (CollUtil.isNotEmpty(userIds)) {
            throw new BizException(HttpStatus.HTTP_UNAUTHORIZED, "当前所选角色已被绑定，无法删除");
        }
        removeBatchByIds(roleIds);

        return Result.success("操作成功", null);
    }
}




