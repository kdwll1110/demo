package com.hyf.demo.controller;

import com.hyf.demo.entity.query.SysRoleQuery;
import com.hyf.demo.entity.query.SysUserQuery;
import com.hyf.demo.result.Result;
import com.hyf.demo.service.ISysMenuService;
import com.hyf.demo.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author ikun
 * @Date 2023/9/15 17:09
 */
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



}
