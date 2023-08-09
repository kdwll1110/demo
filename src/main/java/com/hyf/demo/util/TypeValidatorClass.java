package com.hyf.demo.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
public class TypeValidatorClass implements ConstraintValidator<TypeValidator, Object> {
   // 保存type属性值
   private String values;
   private boolean required;

   @Override
   public void initialize(TypeValidator typeValidator) {
      // 注解内配的值赋值给变量
      this.values = typeValidator.values();
      this.required = typeValidator.required();
   }

   @Override
   public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
      if(this.required) {
         if(null == value || "".equals(value)) {
            return false;
         }
      }
      // 切割获取值
      String[] valueArray = values.split(",");
      Boolean isFlag = false;
      for (int i = 0; i < valueArray.length; i++){
         // 存在一致就跳出循环
         if (valueArray[i] .equals(value.toString())){
            isFlag = true;
            break;
         }
      }
      return isFlag;

   }
}
