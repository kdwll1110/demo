package com.hyf.demo.service;

import com.hyf.demo.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hyf.demo.entity.response.SysMenuResponse;

import java.util.List;


public interface ISysMenuService extends IService<SysMenu> {
    List<SysMenuResponse> queryMenuByUserId(Long userId);
}
