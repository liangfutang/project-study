package com.zjut.study.redis.util;

import com.zjut.study.redis.entity.RedisProperties;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 此处只为单元测试中使用工具
 */
public class JedisPoolUtil {

    private static volatile JedisPool jedisPool = null;

    private JedisPoolUtil(RedisProperties redisProperties) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(jedisPoolConfig.getMaxTotal());
        jedisPoolConfig.setMaxIdle(jedisPoolConfig.getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(jedisPoolConfig.getMaxWaitMillis());
        jedisPoolConfig.setTestOnBorrow(jedisPoolConfig.getTestOnBorrow());
        jedisPool = new JedisPool(jedisPoolConfig, redisProperties.getHost(), redisProperties.getPort());
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
