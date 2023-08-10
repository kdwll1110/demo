package com.hyf.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyf.demo.entity.SysMenu;
import com.hyf.demo.entity.SysRoleMenu;
import com.hyf.demo.entity.response.SysMenuResponse;
import com.hyf.demo.service.ISysMenuService;
import com.hyf.demo.mapper.SysMenuMapper;
import com.hyf.demo.service.ISysRoleMenuService;
import com.hyf.demo.service.ISysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements ISysMenuService {

    @Resource
    private ISysRoleService ISysRoleService;

    @Resource
    private ISysRoleMenuService ISysRoleMenuService;

    /**
     * 根据用户id查询权限
     *
     * @param userId
     * @return
     */
    public List<SysMenuResponse> queryPermissionByUserId(Long userId) {
        //根据userid查询角色id
        Set<Integer> roleIds = ISysRoleService.queryRoleIds(userId);
        //根据角色id查询权限菜单id
        List<Integer> permissionIds = ISysRoleMenuService.lambdaQuery()
                .in(SysRoleMenu::getRoleId, roleIds)
                .list()
                .stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toList());
        //根据权限id查询权限
        List<SysMenu> sysMenuList = lambdaQuery()
                .in(SysMenu::getId, permissionIds)
                .list();

        //把原来的权限菜单 在转换树的时候，一起重新封装了一份
        List<SysMenuResponse> treeNode = createTreeNode(1, sysMenuList);

        //返回树
        return treeNode;
    }

    /**
     * 递归生成树
     *
     * @param parentId
     * @param sysMenuList
     * @return
     */
    public List<SysMenuResponse> createTreeNode(Integer parentId, List<SysMenu> sysMenuList) {
        //创建集合
        List<SysMenuResponse> list = new ArrayList<>();
        //循环
        for (SysMenu permission : sysMenuList) {
            //如果当前菜单的id 等于 传入的父级id
            if (permission.getParentId().equals(parentId)) {
                //封装拷贝
                SysMenuResponse sysMenuResponse = BeanUtil.copyProperties(permission, SysMenuResponse.class);
                //加入到当前集合
                list.add(sysMenuResponse);
                //再给当前菜单继续递归查询，是否有子菜单（参数：当前菜单的id和所有菜单集合）
                sysMenuResponse.setChildren(createTreeNode(Integer.valueOf(permission.getId().toString()), sysMenuList));
            }
        }
        //返回
        return list;
    }

}




