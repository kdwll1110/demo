package com.hyf.demo.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @Author ikun
 * @Date 2023/10/6 10:06
 */
@Data
public class LoginForm {
    @ApiModelProperty("用户名")
    @NotBlank
    private String username;

    @ApiModelProperty("密码")
    @NotBlank
    @Length(min = 6,max = 20,message = "密码长度在6-20位之间")
    private String password;
}
