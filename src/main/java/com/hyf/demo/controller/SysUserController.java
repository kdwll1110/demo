package com.hyf.demo.controller;

import com.hyf.demo.entity.request.SysUserRequest;
import com.hyf.demo.result.Result;
import com.hyf.demo.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


@RestController
@RequestMapping("/user")
@Api(value = "用户接口",tags = "提供用户相关的RestAPI")
public class SysUserController {

    @Resource
    private ISysUserService ISysUserService;

    @PostMapping("login")
    @ApiOperation("登录")
    public Result login(@RequestBody @Valid SysUserRequest sysUserRequest) {
        return Result.success(ISysUserService.login(sysUserRequest));
    }

    @GetMapping("queryRoleInfoAndPermissionInfo")
    @ApiOperation("查询当前用户的角色信息和菜单信息")
    public Result queryRoleInfoAndPermissionInfo(){
        return Result.success(ISysUserService.queryRoleInfoAndPermissionInfo());
    }


}
