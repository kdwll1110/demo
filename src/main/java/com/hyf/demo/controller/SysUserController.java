package com.hyf.demo.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.hyf.demo.annotation.OperationType;
import com.hyf.demo.entity.request.SysUserRequest;
import com.hyf.demo.enums.OperationTypeEnum;
import com.hyf.demo.result.Result;
import com.hyf.demo.service.SysUserService;
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
    private SysUserService sysUserService;

    @PostMapping("login")
    @ApiOperation("用户登录")
    //@OperationType(action=OperationTypeEnum.LOGIN)
    public Result login(@RequestBody @Valid SysUserRequest sysUserRequest) {
        return Result.success(sysUserService.login(sysUserRequest));
    }

    @RequestMapping("isLogin")
    @ApiOperation("检查是否登录")
    public String isLogin() {
        boolean login = true;
        try {
             login = StpUtil.isLogin();
        } catch (Exception e) {
            //并未看见有异常抛出
            throw new RuntimeException(e);
        }
        return "当前会话是否登录：" + login;
    }

    @RequestMapping("tokenInfo")
    @ApiOperation("获取token信息")
    public Result tokenInfo() {
        SaTokenInfo info = StpUtil.getTokenInfo();
        System.out.println("Token 名称：" + info.getTokenName());
        System.out.println("Token 值：" + info.getTokenValue());
        System.out.println("当前是否登录：" + info.getIsLogin());
        System.out.println("当前登录的账号id：" + info.getLoginId());
        System.out.println("当前登录账号的类型：" + info.getLoginType());
        System.out.println("当前登录客户端的设备类型：" + info.getLoginDevice());
        System.out.println("当前 Token 的剩余有效期：" + info.getTokenTimeout()); // 单位：秒，-1代表永久有效，-2代表值不存在
        System.out.println("当前 Token 距离被冻结还剩：" + info.getTokenActiveTimeout()); // 单位：秒，-1代表永久有效，-2代表值不存在
        System.out.println("当前 Account-Session 的剩余有效期" + info.getSessionTimeout()); // 单位：秒，-1代表永久有效，-2代表值不存在
        System.out.println("当前 Token-Session 的剩余有效期" + info.getTokenSessionTimeout()); // 单位：秒，-1代表永久有效，-2代表值不存在
        return Result.success(StpUtil.getTokenInfo());
    }

    @RequestMapping("logout")
    @ApiOperation("退出登录")
    public Result logout() {
        StpUtil.logout();
        return Result.success();
    }

}
