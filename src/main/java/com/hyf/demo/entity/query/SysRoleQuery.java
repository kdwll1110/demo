package com.hyf.demo.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "角色查询对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleQuery {

    @ApiModelProperty(value = "角色名称")
    private String name;
}
