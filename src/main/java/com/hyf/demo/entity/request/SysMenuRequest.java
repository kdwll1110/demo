package com.hyf.demo.entity.request;

import com.baomidou.mybatisplus.annotation.*;
import com.hyf.demo.entity.SysMenu;
import com.hyf.demo.entity.response.SysMenuResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@ApiModel("菜单请求对象")
public class SysMenuRequest implements Serializable {

    @ApiModelProperty(value = "菜单id")
    private Long id;

    @ApiModelProperty(value = "父菜单id")
    private Integer parentId;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "组件路径")
    private String component;

    @ApiModelProperty(value = "路径")
    private String path;

    @ApiModelProperty(value = "状态（1：正常，0：停用）")
    private Integer status;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "菜单类型（0：目录，1：菜单，2：按钮）")
    private Integer type;

    @ApiModelProperty(value = "权限标识")
    private String perms;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "子菜单")
    private List<SysMenu> children;
}