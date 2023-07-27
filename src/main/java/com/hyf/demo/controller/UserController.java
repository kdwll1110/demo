package com.hyf.demo.controller;

import cn.hutool.core.util.DesensitizedUtil;
import com.hyf.demo.constant.CommonConstant;
import com.hyf.demo.dto.UserDTO;
import com.hyf.demo.entity.User;
import com.hyf.demo.service.UserService;
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
    private UserService userService;

    @GetMapping
    @ApiOperation("测试操作")
    public Result test1(){
        return Result.success("hello,world");
    }

    @PostMapping("/login")
    @ApiOperation("登录操作")
    public Result login(@RequestBody @Validated UserDTO userForm){
        return Result.success(CommonConstant.OPERATE_SUCCESS,userService.login(userForm));
    }

    @PostMapping("/insert")
    @ApiOperation("新增用户操作")
    public Result insertUser(@RequestBody @Validated UserDTO userDTO){
        userService.insertUser(userDTO);
        return Result.success(CommonConstant.OPERATE_SUCCESS);
    }


}
