package com.hyf.demo.controller;

import com.hyf.demo.entity.query.SysRoleQuery;
import com.hyf.demo.entity.query.SysUserQuery;
import com.hyf.demo.entity.request.SysRoleRequest;
import com.hyf.demo.result.Result;
import com.hyf.demo.service.ISysMenuService;
import com.hyf.demo.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/role")
@Api(value = "角色接口",tags = "提供角色相关的RestAPI")
@Slf4j
public class SysRoleController {

   @Resource
   private ISysRoleService iSysRoleService;

    @GetMapping("queryAllRole")
    @ApiOperation("查询所有角色信息")
    public Result queryAllRole(){
        return Result.success(iSysRoleService.queryAllRole());
    }

    @GetMapping("queryAllRoleByPage/{current}/{size}")
    @ApiOperation("分页查询所有角色信息")
    public Result queryAllRoleByPage(@PathVariable("current") Integer current,
                                     @PathVariable("size") Integer size,
                                     SysRoleQuery query){
        return Result.success(iSysRoleService.queryAllRoleByPage(current,size,query));
    }

    @GetMapping("queryRoleById/{roleId}")
    @ApiOperation("通过id查询角色信息")
    public Result queryRoleById(@PathVariable("roleId") Integer roleId){
        return Result.success(iSysRoleService.queryRoleById(roleId));
    }

    @PostMapping("addRole")
    @ApiOperation("新增角色信息")
    public Result addRole(@RequestBody SysRoleRequest request){
        return Result.success(iSysRoleService.addRole(request));
    }

    @PutMapping("updateRole")
    @ApiOperation("修改角色信息")
    public Result updateRole(@RequestBody SysRoleRequest request){
        return Result.success(iSysRoleService.updateRole(request));
    }

    @PostMapping("deleteRole")
    @ApiOperation("删除角色信息")
    public Result deleteRole(@RequestBody List<Integer> roleIds){
        return Result.success(iSysRoleService.deleteRole(roleIds));
    }
}
