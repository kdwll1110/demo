package com.hyf.demo.entity.request;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@ApiModel(value = "角色请求对象")
@Data
public class SysRoleRequest implements Serializable {

    @ApiModelProperty(value = "角色id")
    private Long id;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色key")
    private String roleKey;

    @ApiModelProperty(value = "状态（1：正常，0：停用）")
    private Integer status;


}