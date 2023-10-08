package com.hyf.demo.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "用户查询对象")
@Data
public class SysUserQuery {
    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "手机号")
    private String telephone;

    @ApiModelProperty(value = "状态（1：正常，0：停用）")
    private Integer status;
}
