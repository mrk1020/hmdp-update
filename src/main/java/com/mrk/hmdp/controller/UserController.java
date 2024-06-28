package com.mrk.hmdp.controller;


import cn.hutool.core.bean.BeanUtil;
import com.mrk.hmdp.dto.LoginFormDTO;
import com.mrk.hmdp.dto.Result;
import com.mrk.hmdp.dto.UserDTO;
import com.mrk.hmdp.entity.User;
import com.mrk.hmdp.entity.UserInfo;
import com.mrk.hmdp.service.IUserInfoService;
import com.mrk.hmdp.service.IUserService;
import com.mrk.hmdp.utils.UserHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mrk
 * @since 2024-06-27
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    private final IUserInfoService userInfoService;

    /**
     * 发送手机验证码
     * @param phone 手机号
     * @return
     */
    @PostMapping("code")
    public Result sendCode(@RequestParam("phone") String phone) {
        return userService.sendCode(phone);
    }


    /**
     * 短信验证码登录
     * @param loginForm 页面表单参数
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginFormDTO loginForm) {
        return userService.login(loginForm);
    }


    /**
     * 用户登出功能
     * @return
     */
    public Result logout() {
        UserHolder.removeUser();
        return Result.ok();
    }


    /**
     * 返回当前登录用户
     * @return
     */
    @GetMapping("/me")
    public Result me() {
        // 获取当前用户并返回
        UserDTO user = UserHolder.getUser();
        return Result.ok(user);
    }


    /**
     * 根据 id 查询用户信息
     * @param userId 用户 id
     * @return
     */
    @GetMapping("/{id}")
    public Result queryUserById(@PathVariable("id") Long userId) {
        User user = userService.getById(userId);
        if (user == null) {
            return Result.ok();
        }
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        return Result.ok(userDTO);
    }


    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long userId){
        // 查询用户信息详情
        UserInfo info = userInfoService.getById(userId);
        if (info == null) {
            // 没有详情，应该是第一次查看详情
            return Result.ok();
        }
        info.setCreateTime(null);
        info.setUpdateTime(null);
        // 返回
        return Result.ok(info);
    }
}
