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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "SysLog对象", description = "日志表")
@TableName(value ="sys_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysLog implements Serializable {
    @ApiModelProperty(value = "日志id")
    @TableId(value = "log_id", type = IdType.AUTO)
    private Long logId;

    @ApiModelProperty(value = "操作用户")
    @TableField(value = "log_operation_user")
    private String logOperationUser;

    @ApiModelProperty(value = "请求路径")
    @TableField(value = "log_path")
    private String logPath;

    @ApiModelProperty(value = "请求方法")
    @TableField(value = "log_method")
    private String logMethod;

    @ApiModelProperty(value = "请求参数")
    @TableField(value = "log_parameter")
    private String logParameter;

    @ApiModelProperty(value = "响应时间")
    @TableField(value = "log_response_time")
    private String logResponseTime;

    @ApiModelProperty(value = "请求ip")
    @TableField(value = "log_ip")
    private String logIp;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "业务功能描述")
    @TableField(value = "log_action")
    private String logAction;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}