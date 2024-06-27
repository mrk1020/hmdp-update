package com.mrk.hmdp.service.impl;

import com.mrk.hmdp.entity.SeckillVoucher;
import com.mrk.hmdp.mapper.SeckillVoucherMapper;
import com.mrk.hmdp.service.ISeckillVoucherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 秒杀优惠券表，与优惠券是一对一关系 服务实现类
 * </p>
 *
 * @author mrk
 * @since 2024-06-27
 */
@Service
public class SeckillVoucherServiceImpl extends ServiceImpl<SeckillVoucherMapper, SeckillVoucher> implements ISeckillVoucherService {

}
