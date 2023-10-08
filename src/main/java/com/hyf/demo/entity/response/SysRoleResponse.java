package com.hyf.demo.entity.response;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;

@ApiModel(value = "角色响应对象")
@Data
public class SysRoleResponse  implements Serializable {
    @ApiModelProperty(value = "角色id")
    private Long id;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色key")
    private String roleKey;

    @ApiModelProperty(value = "状态（1：正常，0：停用）")
    private Integer status;

}