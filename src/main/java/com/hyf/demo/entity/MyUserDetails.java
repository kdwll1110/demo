package com.hyf.demo.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hyf.demo.entity.response.SysMenuResponse;
import com.hyf.demo.entity.response.SysRoleResponse;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author ikun
 * @Date 2023/8/4 10:32
 */
@Data
public class MyUserDetails implements UserDetails {

    private SysUser sysUser;

    private List<SysRoleResponse> sysRoleResponses;
    private List<SysMenuResponse> sysMenuResponses;

    //存储SpringSecurity所需要的权限信息的集合
    @JSONField(serialize = false)
    private List<GrantedAuthority> authorities;

    public MyUserDetails() {
    }


    public MyUserDetails(SysUser sysUser, List<SysRoleResponse> sysRoleResponses, List<SysMenuResponse> sysMenuResponses) {
        this.sysUser = sysUser;
        this.sysRoleResponses = sysRoleResponses;
        this.sysMenuResponses = sysMenuResponses;
    }

    /**
     * 把权限 封装到这里面 ，由于暂时没有，就拿组件名称代替
     * @return
     */
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities!=null){
            return authorities;
        }
        authorities = sysMenuResponses.stream().
                map(p->new SimpleGrantedAuthority(p.getComponent())).collect(Collectors.toList());
        return authorities;
    }
    @JsonIgnore
    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }
    @JsonIgnore
    @Override
    public String getUsername() {
        return sysUser.getUsername();
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
