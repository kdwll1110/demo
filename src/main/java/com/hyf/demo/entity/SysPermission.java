package com.hyf.demo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 菜单/权限表
 * @TableName sys_permission
 */
@TableName(value ="sys_permission")
@Data
public class SysPermission implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父级菜单id
     */
    @TableField(value = "parent_id")
    private Integer parentId;

    /**
     * 权限名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 组件名称
     */
    @TableField(value = "component")
    private String component;

    /**
     * 访问路径
     */
    @TableField(value = "path")
    private String path;

    /**
     * 状态
     */
    @TableField(value = "status")
    private String status;


    /**
     * 图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 类型(1:目录,2:菜单,3:按钮)
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

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
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private List<SysPermission> children;
}