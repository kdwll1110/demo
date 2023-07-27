package com.hyf.demo.exception;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BizException extends RuntimeException {

    //异常编码
    private int code;

    //异常原因
    private String message;


    public BizException(String message) {
        this.message = message;
    }

    public BizException(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
