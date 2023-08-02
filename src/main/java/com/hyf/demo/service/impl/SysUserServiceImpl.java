package com.hyf.demo.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.temp.SaTempUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyf.demo.annotation.OperationType;
import com.hyf.demo.entity.SysUser;
import com.hyf.demo.entity.request.SysUserRequest;
import com.hyf.demo.entity.response.SysUserResponse;
import com.hyf.demo.exception.BizException;
import com.hyf.demo.service.SysUserService;
import com.hyf.demo.mapper.SysUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{

    @Override
    public SysUserResponse login(SysUserRequest sysUserRequest) {

        SysUser sysUser = lambdaQuery().eq(SysUser::getUsername, sysUserRequest.getUsername())
                .eq(SysUser::getPassword, sysUserRequest.getPassword())
                .one();

        if (ObjectUtil.isEmpty(sysUser)){
            throw new BizException(HttpStatus.HTTP_UNAUTHORIZED,"用户名或密码错误！");
        }

        if (sysUser.getStatus()==0){
            throw new BizException(HttpStatus.HTTP_UNAUTHORIZED,"账号已被停用！");
        }

        //根据账号id，进行登录
        StpUtil.login(sysUser.getId());

        SysUserResponse sysUserResponse = BeanUtil.copyProperties(sysUser, SysUserResponse.class);


        return sysUserResponse;
    }
}




