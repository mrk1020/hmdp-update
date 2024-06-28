package com.mrk.hmdp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mrk.hmdp.dto.LoginFormDTO;
import com.mrk.hmdp.dto.Result;
import com.mrk.hmdp.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mrk
 * @since 2024-06-27
 */
public interface IUserService extends IService<User> {

    Result sendCode(String phone);

    Result login(LoginFormDTO loginForm);
}
