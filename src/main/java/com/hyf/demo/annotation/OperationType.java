package com.hyf.demo.annotation;

import com.hyf.demo.enums.OperationTypeEnum;

import java.lang.annotation.*;

@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OperationType {

    /**
     * 业务功能描述
     */
    OperationTypeEnum action() default OperationTypeEnum.OTHER;
}
