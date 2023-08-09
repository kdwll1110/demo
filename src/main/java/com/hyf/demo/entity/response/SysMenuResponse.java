package com.hyf.demo.entity.response;

import lombok.Data;

import java.util.List;

/**
 * @Author ikun
 * @Date 2023/8/4 16:51
 */
@Data
public class SysMenuResponse {
    /**
     * id
     */
    private Long id;

    /**
     * 父级菜单id
     */
    private Integer parentId;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 组件标题
     */
    private String title;

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
     * 排序
     */
    private Integer sort;

    /**
     * 子菜单
     */
    private List<SysMenuResponse> children;
}
