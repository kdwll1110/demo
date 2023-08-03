package com.hyf.demo.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.hyf.demo.exception.BizException;
import com.hyf.demo.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理bizException
     * @param bizException
     * @return
     */
    @ExceptionHandler(BizException.class)
    public Result handlerBizException(BizException bizException){
        log.info("出现异常——异常原因：{}",bizException.getMessage());
        return Result.fail(bizException.getCode(),bizException.getMessage());
    }

    /**
     * 处理exception
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception exception){
        log.info("全局——异常原因：{}",exception.getMessage());
        return Result.fail(exception.getMessage());
    }

    // 拦截：未登录异常
    @ExceptionHandler(NotLoginException.class)
    public Result handlerException(NotLoginException e) {
        // 打印堆栈，以供调试
        e.printStackTrace();
        log.info("NotLoginException,{}",e.getLoginType());
        // 返回给前端
        return Result.fail(e.getMessage());
    }

    // 拦截：缺少权限异常
    @ExceptionHandler(NotPermissionException.class)
    public Result handlerException(NotPermissionException e) {
        e.printStackTrace();
        return Result.fail("缺少权限：" + e.getPermission());
    }

    // 拦截：缺少角色异常
    @ExceptionHandler(NotRoleException.class)
    public Result handlerException(NotRoleException e) {
        e.printStackTrace();
        return Result.fail("缺少角色：" + e.getRole());
    }


}
