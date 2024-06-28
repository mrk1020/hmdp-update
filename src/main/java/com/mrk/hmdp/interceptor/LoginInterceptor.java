package com.mrk.hmdp.interceptor;

import cn.hutool.http.HttpStatus;
import com.mrk.hmdp.utils.UserHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mrk
 * @create 2024-06-27-17:02
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断当前用户是否已登录（ThreadLocal中是否有用户）
        if (UserHolder.getUser() == null) {
            // 当前用户未登录，直接拦截
            response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
            return false;
        }

        // 用户存在，直接放行
        return true;
    }
}
