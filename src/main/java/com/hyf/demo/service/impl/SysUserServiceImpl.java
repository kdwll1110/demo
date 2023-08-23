package com.hyf.demo.service.impl;

import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyf.demo.entity.MyUserDetails;
import com.hyf.demo.entity.SysUser;
import com.hyf.demo.entity.request.SysUserRequest;
import com.hyf.demo.entity.response.SysMenuResponse;
import com.hyf.demo.entity.response.SysRoleResponse;
import com.hyf.demo.exception.BizException;
import com.hyf.demo.service.ISysMenuService;
import com.hyf.demo.service.ISysRoleService;
import com.hyf.demo.service.ISysUserService;
import com.hyf.demo.mapper.SysUserMapper;
import com.hyf.demo.util.JwtUtil;
import com.hyf.demo.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements ISysUserService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private ISysRoleService ISysRoleService;

    @Resource
    private ISysMenuService ISysMenuService;

    @Override
    public Map<String, Object> login(SysUserRequest sysUserRequest) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(sysUserRequest.getUsername(), sysUserRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        log.info("进来了这里");
        if (authentication==null){
            throw new BizException(HttpStatus.HTTP_UNAUTHORIZED,"用户名或密码错误");
        }

        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();

        String sysUserId = myUserDetails.getSysUser().getId().toString();

        String token = JwtUtil.createJWT(sysUserId);

        //存入security上下文
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //存入redis
        redisTemplate.opsForValue().set("login:"+sysUserId,myUserDetails, 60, TimeUnit.MINUTES);

        //封装
        HashMap<String, Object> map = new HashMap<>();

        map.put("token",token);

        return map;
    }

    /**
     *
     * @return
     */
    @Override
    public Map<String, Object> queryRoleInfoAndPermissionInfo() {

        MyUserDetails myUserDetails = SecurityUtil.getSysUserDetail();

        String username = myUserDetails.getSysUser().getUsername();

        SysUser sysUser = lambdaQuery().eq(SysUser::getUsername, username).one();

        List<SysRoleResponse> sysRoleResponses = ISysRoleService.queryRoleByUserId(sysUser.getId());

        List<SysMenuResponse> sysMenuResponses = ISysMenuService.queryMenuByUserId(sysUser.getId());

        myUserDetails.setSysRoleResponses(sysRoleResponses);

        myUserDetails.setSysMenuResponses(sysMenuResponses);

        Map<String,Object> map = new HashMap<>(3);

        map.put("roleList",sysRoleResponses);

        map.put("menuList",sysMenuResponses);

        return map;
    }


}




