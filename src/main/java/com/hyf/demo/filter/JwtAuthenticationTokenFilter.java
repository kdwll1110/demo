package com.hyf.demo.filter;

import cn.hutool.http.HttpStatus;
import com.hyf.demo.entity.MyUserDetails;
import com.hyf.demo.entity.SysUser;
import com.hyf.demo.exception.BizException;
import com.hyf.demo.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    RedisTemplate redisTemplate;
 
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的token
        String token = httpServletRequest.getHeader("authorization");
        //如果token为空直接放行，由于用户信息没有存放在SecurityContextHolder.getContext()中所以后面的过滤器依旧认证失败符合要求
        if(!StringUtils.hasText(token)){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        String userId;
        try {
            //通过jwt工具类解析token获得userId,如果token过期或非法就会抛异常
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        }catch (Exception e){
            e.printStackTrace();
            throw new BizException(HttpStatus.HTTP_UNAUTHORIZED,"token非法");
        }
        //根据userId从redis中获取用户信息，如果没有该用户就代表该用户没有登录过
        MyUserDetails user = (MyUserDetails) redisTemplate.opsForValue().get("login:"+userId);

        if(Objects.isNull(user)){
            throw new BizException(HttpStatus.HTTP_UNAUTHORIZED,"用户未登录");
        }
        //将用户信息存放在SecurityContextHolder.getContext()，后面的过滤器就可以获得用户信息了。
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(user,null,null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}