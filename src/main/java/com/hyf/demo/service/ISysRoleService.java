package com.hyf.demo.service;

import com.hyf.demo.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hyf.demo.entity.response.SysRoleResponse;

import java.util.List;
import java.util.Set;

public interface ISysRoleService extends IService<SysRole> {

    Set<Integer> queryRoleIds(Long userId);

    List<SysRoleResponse> queryRoleByUserId(Long userId);
}
