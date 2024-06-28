package com.mrk.hmdp.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrk.hmdp.dto.LoginFormDTO;
import com.mrk.hmdp.dto.Result;
import com.mrk.hmdp.dto.UserDTO;
import com.mrk.hmdp.entity.User;
import com.mrk.hmdp.mapper.UserMapper;
import com.mrk.hmdp.service.IUserService;
import com.mrk.hmdp.utils.RegexUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.mrk.hmdp.constants.RedisConstants.*;
import static com.mrk.hmdp.constants.SystemConstants.USER_NICK_NAME_PREFIX;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author mrk
 * @since 2024-06-27
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public Result sendCode(String phone) {
        // 1、判断手机号是否合法
        if (RegexUtils.isPhoneInvalid(phone)) {
            return Result.fail("手机号格式不正确");
        }

        // 2. 判断验证码是否已存在
        String captcha = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + phone);
        if (StrUtil.isNotBlank(captcha)) {
            return Result.fail("验证码已发送，且未过期，请输入验证码登录或注册！");
        }

        // 3. 手机号合法，且验证码不存在 —— 生成验证码，并保存到 redis 中
        String verifyCode = RandomUtil.randomNumbers(4);
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + phone, verifyCode,
                LOGIN_CODE_TTL, TimeUnit.MINUTES);

        // 3、发送验证码
        log.info("验证码: {}", verifyCode);
        return Result.ok();
    }

    @Override
    public Result login(LoginFormDTO loginForm) {

        String phone = loginForm.getPhone();
        String verifyCode = loginForm.getCode();

        if (StrUtil.isBlank(phone) || StrUtil.isBlank(verifyCode)) {
            return Result.fail("请输入手机号及验证码!");
        }

        // 1. 判断手机号是否合法
        if (RegexUtils.isPhoneInvalid(phone)) {
            return Result.fail("手机号格式不正确!");
        }

        // 2. 判断验证码是否正确
        String redisCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + phone);
        if (!verifyCode.equals(redisCode)) {
            return Result.fail("验证码不正确!");
        }

        // 3. 判断手机号是否是已存在的用户
        User user = this.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getPhone, phone));

        if (user == null) {
            // 用户不存在，创建新用户并保存
            user = createUserWithPhone(phone);
        }

        // 4. sa-token 实现的登录，为当前用户创建了一个 token 凭证，且通过 Cookie 上下文返回给了前端
        StpUtil.login(user.getId());

        // 4. 保存用户信息到 redis 中
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(), CopyOptions.create().
                setIgnoreNullValue(true).
                setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));

//        String token = UUID.randomUUID().toString(true);
        String token = StpUtil.getTokenValue();
        String tokenKey = LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);

        return Result.ok(token);
    }


    /**
     * 根据手机号创建用户并保存
     *
     * @param phone 手机号
     * @return
     */
    private User createUserWithPhone(String phone) {
        User user = new User();
        user.setPhone(phone);
        user.setNickName(USER_NICK_NAME_PREFIX + RandomUtil.randomString(10));
        this.save(user);
        return user;
    }
}
