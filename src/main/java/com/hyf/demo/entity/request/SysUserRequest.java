package com.hyf.demo.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户信息请求对象")
public class SysUserRequest {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("用户名")
    @NotBlank
    private String username;

    @ApiModelProperty("密码")
    @NotBlank
    @Length(min = 6,max = 20,message = "密码长度在6-20位之间")
    private String password;

//    @Pattern(regexp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$", message = "手机号格式不正确")
//    @ApiModelProperty("手机号")
//    private String telephone;


}
