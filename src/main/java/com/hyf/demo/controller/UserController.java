package com.hyf.demo.controller;

import com.hyf.demo.constant.CommonConstant;
import com.hyf.demo.entity.request.UserRequest;
import com.hyf.demo.service.SysUserService;
import com.hyf.demo.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/user")
@Api(value = "用户接口",tags = "提供用户相关的RestAPI")
public class UserController {

    @Resource
    private SysUserService sysUserService;

    @GetMapping
    @ApiOperation("测试操作")
    public Result test1(){
        return Result.success("hello,world");
    }

    @PostMapping("/login")
    @ApiOperation("登录操作")
    public Result login(@RequestBody @Validated UserRequest userForm){
        return Result.success(CommonConstant.OPERATE_SUCCESS,sysUserService.login(userForm));
    }

    @PostMapping("/insert")
    @ApiOperation("新增用户操作")
    public Result insertUser(@RequestBody @Validated UserRequest userRequest){
        sysUserService.insertUser(userRequest);
        return Result.success(CommonConstant.OPERATE_SUCCESS);
    }


}
