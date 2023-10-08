package com.hyf.demo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "SysMenu对象", description = "菜单表")
@TableName(value ="sys_menu")
@Data
public class SysMenu implements Serializable {
    @ApiModelProperty(value = "菜单id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "父菜单id")
    @TableField(value = "parent_id")
    private Integer parentId;

    @ApiModelProperty(value = "菜单名称")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value = "组件路径")
    @TableField(value = "component")
    private String component;

    @ApiModelProperty(value = "路径")
    @TableField(value = "path")
    private String path;

    @ApiModelProperty(value = "状态（1：正常，0：停用）")
    @TableField(value = "status")
    private Integer status;

    @ApiModelProperty(value = "菜单图标")
    @TableField(value = "icon")
    private String icon;

    @ApiModelProperty(value = "菜单类型（0：目录，1：菜单，2：按钮）")
    @TableField(value = "type")
    private Integer type;

    @ApiModelProperty(value = "权限标识")
    @TableField(value = "perms")
    private String perms;

    @ApiModelProperty(value = "排序")
    @TableField(value = "sort")
    private Integer sort;

    @ApiModelProperty
    @TableField(value = "version")
    private Integer version;

    @ApiModelProperty(value = "逻辑删除")
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private List<SysMenu> children;
}