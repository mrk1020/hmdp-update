package com.mrk.hmdp.service.impl;

import com.mrk.hmdp.entity.User;
import com.mrk.hmdp.mapper.UserMapper;
import com.mrk.hmdp.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mrk
 * @since 2024-06-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
