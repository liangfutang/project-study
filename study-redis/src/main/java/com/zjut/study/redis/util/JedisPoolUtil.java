package com.zjut.study.redis.util;

import com.zjut.study.redis.entity.RedisProperties;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Objects;

/**
 * 此处只为单元测试中使用工具
 */
public class JedisPoolUtil {

    private JedisPoolUtil() {}

    private static volatile JedisPool jedisPool = null;

    /**
     * 测试@Test中必须先保证 JedisPool 初始化完成，否则会导致获取连接池的时候为空
     * @param redisProperties
     */
    public static void initInstance(RedisProperties redisProperties) {
        Objects.requireNonNull(redisProperties, "连接Redis池配置不能为空");

        if (Objects.isNull(jedisPool)) {
            synchronized (JedisPoolUtil.class) {
                if (Objects.isNull(jedisPool)) {
                    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
                    jedisPoolConfig.setMaxTotal(redisProperties.getMaxTotal());
                    jedisPoolConfig.setMaxIdle(redisProperties.getMaxIdle());
                    jedisPoolConfig.setTestOnBorrow(redisProperties.getTestOnBorrow());

                    jedisPool = new JedisPool(jedisPoolConfig, redisProperties.getHost(), redisProperties.getPort(), 5000, "123456", 3);

                }
            }
        }
    }

    /**
     * 获取资源
     * @return
     */
    public static Jedis getResource() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
        } catch (Exception e) {
            System.out.println("获取redis连接异常:" + e.getMessage());
            release(jedis);
            throw new RuntimeException(e);
        }
        return jedis;
    }

    /**
     * 释放资源
     * @param jedis
     */
    public static void release(Jedis jedis) {
        if (jedis != null) {
            synchronized (JedisPoolUtil.class) {
                jedisPool.returnResource(jedis);
            }
        }
    }
}
