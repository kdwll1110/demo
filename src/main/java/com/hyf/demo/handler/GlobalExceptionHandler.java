package com.hyf.demo.handler;

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

}
