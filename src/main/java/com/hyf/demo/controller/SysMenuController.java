package com.hyf.demo.controller;

import com.hyf.demo.entity.query.SysRoleQuery;
import com.hyf.demo.entity.request.SysMenuRequest;
import com.hyf.demo.result.Result;
import com.hyf.demo.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/menu")
@Api(value = "菜单接口",tags = "提供菜单相关的RestAPI")
public class SysMenuController {

    @Resource
    private ISysMenuService iSysMenuService;

    @GetMapping("queryAllMenu")
    @ApiOperation("查询所有菜单信息")
    public Result queryAllMenu(){
        return Result.success(iSysMenuService.queryAllMenu());
    }

    @GetMapping("queryMenuByRoleId/{roleId}")
    @ApiOperation("根据角色id查询所有菜单信息")
    public Result queryAllRoleByPage(@PathVariable("roleId") Integer roleId){
       return Result.success(iSysMenuService.queryMenuByRoleId(roleId));
    }

    @PutMapping("updateMenuByRoleId/{roleId}")
    @ApiOperation("修改角色菜单信息")
    public Result updateMenuByRoleId(@PathVariable("roleId") Integer roleId, @RequestBody Integer[] menuIds){
        return Result.success(iSysMenuService.updateMenuByRoleId(roleId,menuIds));
    }

    @PostMapping("addMenu")
    @ApiOperation("新增菜单")
    public Result addMenu(@RequestBody SysMenuRequest request){
        return Result.success(iSysMenuService.addMenu(request));
    }

    @GetMapping("queryMenuById/{menuId}")
    @ApiOperation("根据菜单id查询菜单")
    public Result queryMenuById(@PathVariable("menuId") Integer menuId){
        return Result.success(iSysMenuService.queryMenuById(menuId));
    }

    @PutMapping("updateMenu")
    @ApiOperation("修改菜单")
    public Result updateMenu(@RequestBody SysMenuRequest request){
        return Result.success(iSysMenuService.updateMenu(request));
    }

    @DeleteMapping("deleteMenu/{menuId}")
    @ApiOperation("删除菜单")
    public Result deleteMenu(@PathVariable("menuId") Integer menuId){
        return Result.success(iSysMenuService.deleteMenu(menuId));
    }
}
