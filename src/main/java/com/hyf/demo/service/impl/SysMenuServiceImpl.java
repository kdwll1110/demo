package com.hyf.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyf.demo.entity.MyUserDetails;
import com.hyf.demo.entity.SysMenu;
import com.hyf.demo.entity.SysRoleMenu;
import com.hyf.demo.entity.request.SysMenuRequest;
import com.hyf.demo.entity.response.SysMenuResponse;
import com.hyf.demo.entity.response.SysRoleResponse;
import com.hyf.demo.exception.BizException;
import com.hyf.demo.result.Result;
import com.hyf.demo.service.ISysMenuService;
import com.hyf.demo.mapper.SysMenuMapper;
import com.hyf.demo.service.ISysRoleMenuService;
import com.hyf.demo.service.ISysRoleService;
import com.hyf.demo.util.SecurityUtil;
import net.bytebuddy.implementation.bytecode.Throw;
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
    private ISysRoleService iSysRoleService;

    @Resource
    private ISysRoleMenuService iSysRoleMenuService;

    /**
     * 根据用户id查询菜单
     *
     * @param userId
     * @return
     */
    public List<SysMenuResponse> queryMenuByUserId(Long userId) {
        //根据userid查询角色id
        Set<Integer> roleIds = iSysRoleService.queryRoleIds(userId);
        //根据角色id查询权限菜单id
        List<Integer> menuIds = iSysRoleMenuService.lambdaQuery()
                .in(SysRoleMenu::getRoleId, roleIds)
                .list()
                .stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toList());
        return getSysMenuResponses(menuIds);
    }

    private List<SysMenuResponse> getSysMenuResponses(List<Integer> menuIds) {
        //根据权限id查询权限
        List<SysMenu> sysMenuList = lambdaQuery()
                .in(SysMenu::getId, menuIds)
                .list();

        //把原来的权限菜单 在转换树的时候，一起重新封装了一份
        List<SysMenuResponse> treeNode = createTreeNode(1, sysMenuList);

        //返回树
        return treeNode;
    }

    /**
     * 递归生成树
     * @param parentId
     * @param sysMenuList
     * @return
     */
    public List<SysMenuResponse> createTreeNode(Integer parentId, List<SysMenu> sysMenuList) {
        //创建集合
        List<SysMenuResponse> list = new ArrayList<>();
        //循环
        for (SysMenu sysMenu : sysMenuList) {
            //如果当前菜单的id 等于 传入的父级id
            if (sysMenu.getParentId().equals(parentId)) {
                //封装拷贝
                SysMenuResponse sysMenuResponse = BeanUtil.copyProperties(sysMenu, SysMenuResponse.class);
                //加入到当前集合
                list.add(sysMenuResponse);
                //再给当前菜单继续递归查询，是否有子菜单（参数：当前菜单的id和所有菜单集合）
                sysMenuResponse.setChildren(createTreeNode(Integer.valueOf(sysMenu.getId().toString()), sysMenuList));
            }
        }
        //返回
        return list;
    }


    @Override
    public List<SysMenuResponse> queryAllMenu() {
        List<SysMenu> sysMenuList = lambdaQuery().orderByAsc(SysMenu::getSort).list();
        List<SysMenuResponse> treeNode = createTreeNode(1, sysMenuList);
        return treeNode;
    }

    @Override
    public List<SysMenuResponse> queryMenuByRoleId(Integer roleId) {

        List<Integer> menuIds = iSysRoleMenuService.lambdaQuery()
                .eq(SysRoleMenu::getRoleId, roleId)
                .list()
                .stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toList());
        if (CollUtil.isEmpty(menuIds)) {
            throw new BizException(HttpStatus.HTTP_BAD_REQUEST,"该角色尚未绑定菜单");
        }

        return getSysMenuResponses(menuIds);
    }

    @Override
    public Result updateMenuByRoleId(Integer roleId, Integer[] menuIds) {

        if (menuIds==null || menuIds.length<1){
            throw new BizException(HttpStatus.HTTP_BAD_REQUEST,"请选择菜单");
        }
        //前端传的值
        List<Integer> ids = CollUtil.toList(menuIds);
        //数据库查询
        List<SysRoleMenu> menuData = iSysRoleMenuService.lambdaQuery().
                in(SysRoleMenu::getRoleId, roleId).list();

        //获取该删除的
        List<SysRoleMenu> removeNeed = menuData.stream().filter(i -> !ids.contains(i.getMenuId())).collect(Collectors.toList());
        iSysRoleMenuService.removeByIds(removeNeed.stream().map(SysRoleMenu::getId).collect(Collectors.toList()));

        //获取要新增的
        List<Integer> menuIdsData = menuData.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        List<Integer> addNeed = ids.stream().filter(i -> !menuIdsData.contains(i)).collect(Collectors.toList());

        List<SysRoleMenu> roleMenus = addNeed.stream().map(m -> new SysRoleMenu(roleId, m)).collect(Collectors.toList());
        iSysRoleMenuService.saveBatch(roleMenus);

        return Result.success("操作成功",null);
    }

    @Override
    public Result addMenu(SysMenuRequest request) {
        System.out.println("request = " + request);
        SysMenu sysMenu = BeanUtil.copyProperties(request, SysMenu.class);
        SysMenu parent = lambdaQuery().eq(SysMenu::getId, request.getParentId()).one();
        String parentPath = parent.getPath();

        if (StrUtil.isBlank(parentPath)){
            parentPath = "";
        }

        sysMenu.setPath(parentPath + "/" + sysMenu.getPath());
        save(sysMenu);

        return Result.success("操作成功",null);
    }

    @Override
    public SysMenuResponse queryMenuById(Integer menuId) {
        SysMenu sysMenu = getById(menuId);
        if (sysMenu==null){
            throw new BizException(HttpStatus.HTTP_BAD_REQUEST,"菜单不存在");
        }
        return BeanUtil.copyProperties(sysMenu,SysMenuResponse.class);
    }

    @Override
    public Result updateMenu(SysMenuRequest request) {
        SysMenu sysMenu = BeanUtil.copyProperties(request, SysMenu.class);
        if (sysMenu.getPath().indexOf("/") != 0){
            sysMenu.setPath("/" + sysMenu.getPath());
        }
        updateById(sysMenu);
        return Result.success("操作成功",null);
    }

    @Override
    public Result deleteMenu(Integer menuId) {
        MyUserDetails sysUserDetail = SecurityUtil.getSysUserDetail();
        Long userId = sysUserDetail.getSysUser().getId();
        List<SysRoleResponse> sysRoleResponses = iSysRoleService.queryRoleByUserId(userId);
        boolean exits = sysRoleResponses.stream().anyMatch(i -> "SUPER_ADMIN".equals(i.getRoleKey()));
        if (!exits){
            throw new BizException(HttpStatus.HTTP_UNAUTHORIZED,"非超级管理员不能删除");
        }
        removeById(menuId);
        //删除菜单后，删除角色菜单的关联
        List<SysRoleMenu> list = iSysRoleMenuService.lambdaQuery().in(SysRoleMenu::getMenuId, menuId).list();
        if (CollUtil.isNotEmpty(list)){
            iSysRoleMenuService.removeByIds(list.stream().map(SysRoleMenu::getId).collect(Collectors.toList()));
        }

        return Result.success("操作成功",null);
    }


}




