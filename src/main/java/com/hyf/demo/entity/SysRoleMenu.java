package com.hyf.demo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色菜单关联表
 * @TableName sys_role_menu
 */
@TableName(value ="sys_role_menu")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleMenu implements Serializable {

    public SysRoleMenu(Integer roleId, Integer menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色id
     */
    @TableField(value = "role_id")
    private Integer roleId;

    /**
     * 权限id
     */
    @TableField(value = "menu_id")
    private Integer menuId;

    /**
     * 乐观锁
     */
    @TableField(value = "version")
    private Integer version;

    /**
     * 逻辑删除(1:已删除，0:未删除)
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}