package com.mrk.hmdp.service.impl;

import com.mrk.hmdp.entity.Blog;
import com.mrk.hmdp.mapper.BlogMapper;
import com.mrk.hmdp.service.IBlogService;
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
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {

}
