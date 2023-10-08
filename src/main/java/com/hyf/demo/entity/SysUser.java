package com.hyf.demo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@TableName(value ="sys_user")
@Data
@Accessors(chain = true)
@ApiModel(value = "SysUser对象", description = "用户表")
public class SysUser implements Serializable {
    @ApiModelProperty(value = "用户id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户名")
    @TableField(value = "username")
    private String username;

    @ApiModelProperty(value = "用户密码")
    @TableField(value = "password")
    private String password;

    @ApiModelProperty(value = "用户手机号")
    @TableField(value = "telephone")
    private String telephone;

    @ApiModelProperty(value = "邮箱")
    @TableField(value = "email")
    private String email;

    @ApiModelProperty(value = "状态（1：正常，0：停用）")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "逻辑删除")
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value = "乐观锁")
    @TableField(value = "version")
    private Integer version;

    @ApiModelProperty(value = "创建时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}