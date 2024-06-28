package com.mrk.hmdp.constants;

/**
 * @author mrk
 * @create 2024-06-27-16:32
 */
public class RedisConstants {

    // 登录用户前缀
    public static final String LOGIN_USER_KEY = "login:user:";

    // 登录用户有效期 3600 min
    public static final Long LOGIN_USER_TTL = 3600L;

    // 登录验证码前缀
    public static final String LOGIN_CODE_KEY = "login:captcha:";

    // 登录验证码有效期 200 min
    public static final Long LOGIN_CODE_TTL = 200L;

    // 空对象的有效期
    public static final Long CACHE_NULL_TTL = 2L;


    // 店铺缓存数据有效期
    public static final Long CACHE_SHOP_TTL = 30L;

    // 店铺缓存数据逻辑过期时间
    public static final Long CACHE_SHOP_LOGICAL_TTL = 20L;

    // 缓存店铺数据前缀
    public static final String CACHE_SHOP_KEY = "cache:shop:";

    // 缓存店铺类型前缀
    public static final String CACHE_SHOP_TYPE_KEY = "cache:type:";

    // 店铺类型缓存数据有效期
    public static final Long CACHE_SHOP_TYPE_TTL = 30L;


    public static final String LOCK_SHOP_KEY = "lock:shop:";
    public static final String LOCK_ORDER_KEY = "lock:order:";
    public static final Long LOCK_SHOP_TTL = 10L;

    public static final String ID_PREFIX = "icr:";
    public static final String SECKILL_STOCK_KEY = "seckill:stock:";

    public static final String SECKILL_VOUCHER_ORDER = "order:";
    public static final String BLOG_LIKED_KEY = "blog:liked:";

    public static final String FOLLOW_KEY = "follows:";
    public static final String FEED_KEY = "feed:";
    public static final String SHOP_GEO_KEY = "shop:geo:";
    public static final String USER_SIGN_KEY = "sign:";
}
