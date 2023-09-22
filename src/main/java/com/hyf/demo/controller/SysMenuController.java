package com.hyf.demo.controller;

import com.hyf.demo.entity.query.SysRoleQuery;
import com.hyf.demo.result.Result;
import com.hyf.demo.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author ikun
 * @Date 2023/9/18 10:02
 */
@RestController
@RequestMapping("/menu")
@Api(value = "菜单接口",tags = "提供菜单相关的RestAPI")
public class SysMenuController {

    @Resource
    private ISysMenuService iSysMenuService;

    @RequestMapping("queryAllMenu")
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
    @ApiOperation("修改角色菜单权限信息")
    public Result updateMenuByRoleId(@PathVariable("roleId") Integer roleId, @RequestBody Integer[] menuIds){
        return Result.success(iSysMenuService.updateMenuByRoleId(roleId,menuIds));
    }
}
