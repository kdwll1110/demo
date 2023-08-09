package com.hyf.demo.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.PageUtil;
import cn.hutool.http.HttpStatus;
import com.hyf.demo.annotation.OperationType;
import com.hyf.demo.entity.request.SysUserRequest;
import com.hyf.demo.enums.OperationTypeEnum;
import com.hyf.demo.exception.BizException;
import com.hyf.demo.result.Result;
import com.hyf.demo.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


@RestController
@RequestMapping("/user")
@Api(value = "用户接口",tags = "提供用户相关的RestAPI")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @PostMapping("login")
    @ApiOperation("登录")
    public Result login(@RequestBody @Valid SysUserRequest sysUserRequest) {
        return Result.success(sysUserService.login(sysUserRequest));
    }

    @GetMapping("queryRoleInfoAndPermissionInfo")
    @ApiOperation("查询角色信息和菜单信息")
    public Result queryRoleInfoAndPermissionInfo(){

        return Result.success(sysUserService.queryRoleInfoAndPermissionInfo());
    }

    @GetMapping
    public String hello(){
        return "Hello world";
    }

}
