package com.hyf.demo.entity.request;

import com.baomidou.mybatisplus.annotation.*;
import com.hyf.demo.entity.response.SysMenuResponse;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("菜单信息请求对象")
public class SysMenuRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 父级菜单id
     */
    private Integer parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 组件名称
     */
    private String component;

    /**
     * 访问路径
     */
    private String path;

    /**
     * 状态
     */
    private Integer status;


    /**
     * 图标
     */
    private String icon;

    /**
     * 类型(1:目录,2:菜单,3:按钮)
     */
    private Integer type;

    /**
     *权限标识
     */
    private String perms;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 子菜单
     */
    private List<SysMenuResponse> children;
}