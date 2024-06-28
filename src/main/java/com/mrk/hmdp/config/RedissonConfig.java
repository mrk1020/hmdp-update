package com.mrk.hmdp.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mrk
 * @create 2024-06-27-17:20
 */

@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    /**
     * 创建 Redisson 配置对象，然后交给 IOC 管理
     * @return
     */
    @Bean
    public RedissonClient redissonClient() {
        // 获取 Redisson 配置对象
        Config config = new Config();
        // 添加 redis 地址，这里添加的是单节点地址，也可以通过 config.userClusterServers() 添加集群地址
        config.useSingleServer().setAddress("redis://" + this.host + ":" + this.port)
                .setPassword(this.password);

        // 获取 RedisClient 对象，并交给 IOC 进行管理
        return Redisson.create(config);
    }
}
