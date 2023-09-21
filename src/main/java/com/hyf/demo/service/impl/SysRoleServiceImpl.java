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
import com.hyf.demo.entity.response.SysRoleResponse;
import com.hyf.demo.exception.BizException;
import com.hyf.demo.service.ISysRoleService;
import com.hyf.demo.mapper.SysRoleMapper;
import com.hyf.demo.service.ISysUserRoleService;
import com.hyf.demo.util.PageUtil;
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

    @Override
    public List<SysRoleResponse> queryAllRole() {
        List<SysRole> list = lambdaQuery().list();
        List<SysRoleResponse> sysRoleResponses = BeanUtil.copyToList(list, SysRoleResponse.class);
        return sysRoleResponses;
    }

    @Override
    public PageUtil queryAllRoleByPage(Integer current, Integer size, SysRoleQuery query) {

        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();

        if(query!=null){
            if (StrUtil.isNotBlank(query.getName())){
                queryWrapper.eq(SysRole::getName,query.getName());
            }
        }

        Page<SysRole> page = baseMapper.selectPage(new Page<>(current, size), queryWrapper);

        List<SysRoleResponse> list = BeanUtil.copyToList(page.getRecords(), SysRoleResponse.class);

        return new PageUtil(page.getCurrent(),page.getSize(),page.getTotal(),list);
    }


}




