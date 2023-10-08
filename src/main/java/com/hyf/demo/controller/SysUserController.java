package com.hyf.demo.controller;

import com.hyf.demo.entity.query.SysUserQuery;
import com.hyf.demo.entity.request.LoginForm;
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
import java.util.List;


@RestController
@RequestMapping("/user")
@Api(value = "用户接口",tags = "提供用户相关的RestAPI")
@Slf4j
public class SysUserController {

    @Resource
    private ISysUserService ISysUserService;

    @PostMapping("login")
    @ApiOperation("登录")
    public Result login(@RequestBody @Valid LoginForm loginForm) {
        return Result.success(ISysUserService.login(loginForm));
    }

    @GetMapping("queryMenuByToken")
    @ApiOperation("查询当前的菜单信息")
    public Result queryMenuByToken(){
        return Result.success(ISysUserService.queryRoleInfoAndPermissionInfo());
    }

    @GetMapping("helloworld")
    @ApiOperation("测试接口")
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

    @GetMapping("queryUserById/{userId}")
    @ApiOperation("根据用户id查询用户信息")
    public Result queryUserById(@PathVariable("userId") Long userId){
        return Result.success(ISysUserService.queryUserById(userId));
    }

    @PutMapping ("updateUser")
    @ApiOperation("修改用户信息")
    public Result updateUser(@RequestBody @Valid SysUserRequest sysUserRequest){
        return Result.success(ISysUserService.updateUser(sysUserRequest));
    }

    @PostMapping ("addUser")
    @ApiOperation("新增用户信息")
    public Result addUser(@RequestBody SysUserRequest sysUserRequest){
        return Result.success(ISysUserService.addUser(sysUserRequest));
    }

    @PostMapping ("deleteUser")
    @ApiOperation("删除用户信息")
    public Result deleteUser(@RequestBody List<Integer> userIds){
        return Result.success(ISysUserService.deleteUser(userIds));
    }

}
