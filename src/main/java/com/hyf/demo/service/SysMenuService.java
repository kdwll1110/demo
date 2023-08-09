package com.hyf.demo.service;

import com.hyf.demo.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hyf.demo.entity.response.SysMenuResponse;

import java.util.List;


public interface SysMenuService extends IService<SysMenu> {
    List<SysMenuResponse> queryPermissionByUserId(Long userId);
}
