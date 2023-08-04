package com.hyf.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hyf.demo.entity.response.SysPermissionResponse;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @Author ikun
 * @Date 2023/8/4 10:32
 */
@Data
public class MyUserDetails implements UserDetails {

    private SysUser sysUser;

    private List<SysPermissionResponse> sysPermissionResponses;

    public MyUserDetails() {
    }

    public MyUserDetails(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public MyUserDetails(SysUser sysUser, List<SysPermissionResponse> sysPermissionResponses) {
        this.sysUser = sysUser;
        this.sysPermissionResponses = sysPermissionResponses;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
