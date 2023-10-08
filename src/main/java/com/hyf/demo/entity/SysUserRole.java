package com.hyf.demo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName(value ="sys_user_role")
@Data
@ApiModel(value = "SysUserRole对象", description = "用户角色关联表")
@NoArgsConstructor
@AllArgsConstructor
public class SysUserRole implements Serializable {

    @ApiModelProperty(value = "用户角色关联表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    @ApiModelProperty(value = "用户id")
    @TableField(value = "user_id")
    private Integer userId;


    @ApiModelProperty(value = "角色id")
    @TableField(value = "role_id")
    private Integer roleId;

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

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public SysUserRole(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}