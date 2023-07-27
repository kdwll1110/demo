package com.hyf.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


/**
 * 用户表
 * @TableName user
 */
@TableName(value ="sys_user")
@Data
@ToString
@ApiModel("用户实体类")
public class User implements Serializable {
    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    @ApiModelProperty("用户id")
    private Long userId;

    /**
     * 用户名
     */
    @TableField(value = "user_name")
    @ApiModelProperty("用户名")
    private String userName;

    /**
     * 用户密码
     */
    @TableField(value = "user_password")
    @ApiModelProperty("用户密码")
    private String userPassword;

    /**
     * 用户手机号
     */
    @TableField(value = "user_phone_number")
    @ApiModelProperty("用户手机号")
    private String userPhoneNumber;

    /**
     * 用户收货地址
     */
    @TableField(value = "user_shipping_address")
    @ApiModelProperty("用户收货地址")
    private String userShippingAddress;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}