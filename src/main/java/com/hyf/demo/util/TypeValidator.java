package com.hyf.demo.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * type验证器注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER,ElementType.FIELD})
//需要关联对应的校验器类的class
@Constraint(validatedBy = TypeValidatorClass.class)

public @interface TypeValidator {
    //type类型的有效值可以有多个，用,隔开
    String values();
    // 是否必填
    boolean required() default false;
    // 提示内容
    String message() default "type不存在";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
