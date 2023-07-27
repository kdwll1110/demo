package com.hyf.demo.annotation;

import java.lang.annotation.*;

@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OperationType {

    /**
     * 业务功能描述
     */
    String action() default "";

}
