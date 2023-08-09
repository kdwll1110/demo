package com.hyf.demo.util;

import cn.hutool.http.HttpStatus;
import com.hyf.demo.entity.MyUserDetails;
import com.hyf.demo.exception.BizException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author ikun
 * @Date 2023/8/9 9:39
 */
public class SecurityUtil {

    public static MyUserDetails getSysUserDetail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            return (MyUserDetails) authentication.getPrincipal();
        } catch (Exception e) {
            throw new BizException(HttpStatus.HTTP_UNAUTHORIZED,"获取登录用户信息异常");
        }
    }
}
