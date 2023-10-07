package com.hyf.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyf.demo.annotation.OperationType;
import com.hyf.demo.entity.MyUserDetails;
import com.hyf.demo.entity.SysUser;
import com.hyf.demo.entity.SysUserRole;
import com.hyf.demo.entity.query.SysUserQuery;
import com.hyf.demo.entity.request.LoginForm;
import com.hyf.demo.entity.request.SysUserRequest;
import com.hyf.demo.entity.response.SysMenuResponse;
import com.hyf.demo.entity.response.SysRoleResponse;
import com.hyf.demo.entity.response.SysUserResponse;
import com.hyf.demo.enums.OperationTypeEnum;
import com.hyf.demo.exception.BizException;
import com.hyf.demo.result.Result;
import com.hyf.demo.service.ISysMenuService;
import com.hyf.demo.service.ISysRoleService;
import com.hyf.demo.service.ISysUserRoleService;
import com.hyf.demo.service.ISysUserService;
import com.hyf.demo.mapper.SysUserMapper;
import com.hyf.demo.util.JwtUtil;
import com.hyf.demo.util.PageUtil;
import com.hyf.demo.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
        implements ISysUserService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private ISysRoleService iSysRoleService;

    @Resource
    private ISysMenuService iSysMenuService;

    @Resource
    private ISysUserRoleService iSysUserRoleService;

    @OperationType(action = OperationTypeEnum.LOGIN)
    @Override
    public Map<String, Object> login(LoginForm loginForm) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if (authentication == null) {
            throw new BizException(HttpStatus.HTTP_UNAUTHORIZED, "用户名或密码错误");
        }

        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();

        String sysUserId = myUserDetails.getSysUser().getId().toString();

        String token = JwtUtil.createJWT(sysUserId);

        //存入security上下文
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //存入redis
        redisTemplate.opsForValue().set("login:" + sysUserId, myUserDetails, 60, TimeUnit.MINUTES);

        //封装
        HashMap<String, Object> map = new HashMap<>();

        map.put("token", token);
        log.info("第一步");
        return map;
    }

    /**
     * @return
     */
    @Override
    public Map<String, Object> queryRoleInfoAndPermissionInfo() {
        log.info("第1.5步");
        MyUserDetails myUserDetails = SecurityUtil.getSysUserDetail();

        String username = myUserDetails.getSysUser().getUsername();

        SysUser sysUser = lambdaQuery().eq(SysUser::getUsername, username).one();

        List<SysRoleResponse> sysRoleResponses = iSysRoleService.queryRoleByUserId(sysUser.getId());

        List<SysMenuResponse> sysMenuResponses = iSysMenuService.queryMenuByUserId(sysUser.getId());

        myUserDetails.setSysRoleResponses(sysRoleResponses);

        myUserDetails.setSysMenuResponses(sysMenuResponses);

        Map<String, Object> map = new HashMap<>(3);

        map.put("roleList", sysRoleResponses);

        map.put("menuList", sysMenuResponses);
        log.info("第二步");
        return map;
    }


    @Override
    public PageUtil<SysUserResponse> queryAllUserByPage(Integer current, Integer size, SysUserQuery query) {

        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();

        if (query != null) {
            if (StrUtil.isNotBlank(query.getUsername())) {
                queryWrapper.like(SysUser::getUsername, query.getUsername());
            }
            if (StrUtil.isNotBlank(query.getTelephone())) {
                queryWrapper.like(SysUser::getTelephone, query.getTelephone());
            }
            if (query.getStatus() != null) {
                queryWrapper.eq(SysUser::getStatus, query.getStatus());
            }
        }

        Page<SysUser> page = baseMapper.selectPage(new Page<>(current, size), queryWrapper);

        List<SysUserResponse> list = BeanUtil.copyToList(page.getRecords(), SysUserResponse.class);

        return new PageUtil(page.getCurrent(), page.getSize(), page.getTotal(), list);
    }

    @Override
    public SysUserResponse queryUserById(Long userId) {
        SysUser sysUser = baseMapper.selectById(userId);
        if (sysUser == null) {
            throw new BizException(HttpStatus.HTTP_BAD_REQUEST, "用户不存在");
        }
        SysUserResponse sysUserResponse = BeanUtil.copyProperties(sysUser, SysUserResponse.class);

        Set<Integer> roleIds = iSysRoleService.queryRoleIds(sysUser.getId());
        sysUserResponse.setRoleIds(roleIds);
        return sysUserResponse;
    }

    @Override
    public Result updateUser(SysUserRequest sysUserRequest) {
        System.out.println("sysUserRequest = " + sysUserRequest);
        SysUser sysUser = lambdaQuery().eq(SysUser::getId, sysUserRequest.getId()).one();
        if (sysUser == null) {
            throw new BizException(HttpStatus.HTTP_BAD_REQUEST, "用户不存在");
        }
        SysUser updateUser = BeanUtil.copyProperties(sysUserRequest, SysUser.class);
        updateById(updateUser);

        Long userId = sysUser.getId();
        //当前绑定的角色id
        Set<SysUserRole> sysUserRoles = iSysUserRoleService.lambdaQuery().in(SysUserRole::getUserId, userId).list().stream().collect(Collectors.toSet());
        Set<Integer> currentIds = sysUserRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toSet());

        //修改后的角色id
        Set<Integer> reqRoleIds = sysUserRequest.getRoleIds();

        //得到需要删除的
        Set<SysUserRole> needRemove = sysUserRoles.stream().filter(i -> !reqRoleIds.contains(i.getRoleId())).collect(Collectors.toSet());
        iSysUserRoleService.removeByIds(needRemove);

        Set<Integer> collect = reqRoleIds.stream().filter(i -> !currentIds.contains(i)).collect(Collectors.toSet());
        List<SysUserRole> roles = collect.stream().map(i -> new SysUserRole(Integer.valueOf(userId.toString()), i)).collect(Collectors.toList());
        iSysUserRoleService.saveBatch(roles);


        return Result.success("操作成功", null);
    }

    @Override
    public Result addUser(SysUserRequest sysUserRequest) {
        SysUser sysUser = BeanUtil.copyProperties(sysUserRequest, SysUser.class);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String initialPassword = encoder.encode("123456");
        sysUser.setPassword(initialPassword);
        save(sysUser);

        Set<Integer> roleIds = sysUserRequest.getRoleIds();
        List<SysUserRole> sysUserRoleList = new ArrayList<>();
        roleIds.stream().forEach(id -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(Integer.valueOf(sysUser.getId().toString()));
            sysUserRole.setRoleId(id);
            sysUserRoleList.add(sysUserRole);
        });
        iSysUserRoleService.saveBatch(sysUserRoleList);
        return Result.success("操作成功",null);
    }

    @Override
    public Result deleteUser(List<Integer> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            throw new BizException(HttpStatus.HTTP_BAD_REQUEST, "请选择要删除的角色");
        }
        removeBatchByIds(userIds);

        return Result.success("操作成功", null);
    }


}




