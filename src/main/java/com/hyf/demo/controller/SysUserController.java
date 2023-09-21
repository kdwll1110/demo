package com.hyf.demo.controller;

import com.hyf.demo.entity.query.SysUserQuery;
import com.hyf.demo.entity.request.SysUserRequest;
import com.hyf.demo.result.Result;
import com.hyf.demo.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequestMapping("/user")
@Api(value = "用户接口",tags = "提供用户相关的RestAPI")
@Slf4j
public class SysUserController {

    @Resource
    private ISysUserService ISysUserService;

    @PostMapping("login")
    @ApiOperation("登录")
    public Result login(@RequestBody @Valid SysUserRequest sysUserRequest) {
        return Result.success(ISysUserService.login(sysUserRequest));
    }

    @GetMapping("queryMenuByToken")
    @ApiOperation("查询当前的菜单信息")
    public Result queryMenuByToken(){
        return Result.success(ISysUserService.queryRoleInfoAndPermissionInfo());
    }

    @GetMapping("hello")
    public Result hello(){
        return Result.success("GG-Bond");
    }


    @GetMapping("queryAllUserByPage/{current}/{size}")
    @ApiOperation("分页查询所有用户信息")
    public Result queryAllUserByPage(@PathVariable("current") Integer current,
                                     @PathVariable("size") Integer size,
                                     SysUserQuery query){
        return Result.success(ISysUserService.queryAllUserByPage(current,size,query));
    }

}
