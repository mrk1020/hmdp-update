package com.mrk.hmdp.interceptor;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.mrk.hmdp.dto.UserDTO;
import com.mrk.hmdp.utils.UserHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.mrk.hmdp.constants.RedisConstants.LOGIN_USER_KEY;
import static com.mrk.hmdp.constants.RedisConstants.LOGIN_USER_TTL;

/**
 * @author mrk
 * @create 2024-06-27-17:08
 */
@RequiredArgsConstructor
public class RefreshTokenInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 获取请求头中的 token，并判断 token 是否存在
        String token = request.getHeader("authorization");
//        String token = request.getHeader("sa-token");

        if (StrUtil.isBlank(token)) {
            // token 不存在，说明当前用户未登录，不需要刷新直接放行
            return true;
        }

        // 2. 判断用户是否存在
        String tokenKey = LOGIN_USER_KEY + token;
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(tokenKey);
        if (userMap.isEmpty()) {
            // 用户不存在，说明当前用户未登录，不需要刷新直接放行
            return true;
        }

        // 3. 用户存在，则将用户信息保存到 ThreadLocal 中，方便后续逻辑处理
        UserDTO userDTO = BeanUtil.fillBeanWithMap(userMap, new UserDTO(), false);
        UserHolder.saveUser(userDTO);

        // 4. 刷新 token 有效期
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);

        // 5. 放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }
}
