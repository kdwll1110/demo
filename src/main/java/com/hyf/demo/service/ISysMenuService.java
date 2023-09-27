package com.hyf.demo.service;

import com.hyf.demo.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hyf.demo.entity.request.SysMenuRequest;
import com.hyf.demo.entity.response.SysMenuResponse;
import com.hyf.demo.result.Result;

import java.util.List;


public interface ISysMenuService extends IService<SysMenu> {
    List<SysMenuResponse> queryMenuByUserId(Long userId);

    List<SysMenuResponse> queryAllMenu();

    List<SysMenuResponse> queryMenuByRoleId(Integer roleId);

    Result updateMenuByRoleId(Integer roleId, Integer[] menuIds);

    Result addMenu(SysMenuRequest request);

    SysMenuResponse queryMenuById(Integer menuId);

    Result updateMenu(SysMenuRequest request);

    Result deleteMenu(Integer menuId);
}
