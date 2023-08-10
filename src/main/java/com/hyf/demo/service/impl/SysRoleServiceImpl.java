package com.hyf.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyf.demo.entity.SysRole;
import com.hyf.demo.entity.SysUserRole;
import com.hyf.demo.entity.response.SysRoleResponse;
import com.hyf.demo.exception.BizException;
import com.hyf.demo.service.ISysRoleService;
import com.hyf.demo.mapper.SysRoleMapper;
import com.hyf.demo.service.ISysUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
     * @param userId
     * @return
     */
    public Set<Integer> queryRoleIds(Long userId) {
        //根据userid查询角色id
        Set<Integer> roleIds = ISysUserRoleService.lambdaQuery()
                .eq(SysUserRole::getUserId, userId)
                .eq(SysUserRole::getIsDeleted, 0)
                .list().stream().map(SysUserRole::getRoleId).collect(Collectors.toSet());
        if (CollUtil.isEmpty(roleIds)){
            throw new BizException(HttpStatus.HTTP_UNAUTHORIZED,"当前账号已被停用");
        }
        return roleIds;
    }

    /**
     * 返回角色信息
     * @param userId
     * @return
     */
    public List<SysRoleResponse> queryRoleByUserId(Long userId) {
        Set<Integer> roleIds = queryRoleIds(userId);
        List<SysRole> sysRoles = lambdaQuery().in(SysRole::getId, roleIds).list();
        return BeanUtil.copyToList(sysRoles,SysRoleResponse.class);
    }


}




