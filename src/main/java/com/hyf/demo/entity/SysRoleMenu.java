package com.hyf.demo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "SysRoleMenu对象", description = "角色菜单关联表")
@TableName(value ="sys_role_menu")
@Data
public class SysRoleMenu implements Serializable {

    public SysRoleMenu(Integer roleId, Integer menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }

    @ApiModelProperty(value = "角色菜单关联表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "角色id")
    @TableField(value = "role_id")
    private Integer roleId;

    @ApiModelProperty
    @TableField(value = "menu_id")
    private Integer menuId;

    @ApiModelProperty(value = "乐观锁")
    @TableField(value = "version")
    private Integer version;

    @ApiModelProperty(value = "逻辑删除")
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}