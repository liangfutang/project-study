package com.zjut.study.redis.util;


import com.zjut.study.redis.entity.RedisProperties;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Objects;

/**
 * Redission操作的工具类
 */
public class RedissionUtils {

    private RedissionUtils() {}

    private volatile static RedissonClient redissonClient;

    /**
     * 测试@Test中必须先保证 JedisPool 初始化完成，否则会导致获取连接池的时候为空
     * 本案例中初始化不会有竞争，所以直接初始化
     * @param redisProperties
     */
    public static RedissonClient initInstance(RedisProperties redisProperties) {
        if (Objects.nonNull(redisProperties)) {
            Config config = new Config();
            config.useSingleServer()
                    .setAddress("redis://127.0.0.1:6379")
                    .setDatabase(2).setPassword(redisProperties.getPassword())
                    .setTimeout(redisProperties.getTimeout())
                    .setConnectionPoolSize(redisProperties.getConnectionPoolSize())
                    .setConnectionMinimumIdleSize(redisProperties.getConnectionMinimumIdleSize());
            redissonClient = Redisson.create(config);
        }
        return redissonClient;
    }

    /**
     * 获取锁
     * @param key
     * @return
     */
    public static RLock getRLock(String key) {
        return redissonClient.getLock(key);
    }

    public static void closeRedisson(){
        redissonClient.shutdown();
        System.out.println("客户端关闭");
    }

}
