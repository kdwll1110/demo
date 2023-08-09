package com.hyf.demo.entity.response;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;

/**
 * 角色表
 * @TableName sys_role
 */
@Data
public class SysRoleResponse  implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 角色名称
     */
    private String name;

}