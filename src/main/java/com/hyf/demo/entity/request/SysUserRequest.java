package com.hyf.demo.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户请求对象")
public class SysUserRequest {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("用户名")
    @NotBlank
    private String username;

    @Pattern(regexp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$", message = "手机号格式不正确")
    @ApiModelProperty("手机号")
    private String telephone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("角色列表")
    private Set<Integer> roleIds;


}
