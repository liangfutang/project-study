package com.zjut.study.redis.util;

import redis.clients.jedis.Jedis;

import java.util.function.Supplier;

/**
 * redis相关测试的时候减少异常捕获带来的代码冗余
 */
public class JedisHandlerUtil {

    /**
     * 简单代理JedisPool测试相关代码
     * @param handler
     */
    public static void handlerPool(Supplier<? extends Jedis> handler) {
        Jedis jedis = null;
        try {
            jedis = handler.get();
        } catch (Exception e) {
            System.out.println("异常:" + e.getMessage());
        } finally {
            JedisPoolUtil.release(jedis);
            System.out.println("释放jedis资源到连接池");
        }
    }

    /**
     * 简单代理JedisPool测试相关代码
     * @param handler
     */
    public static void handler(Supplier<? extends Jedis> handler) {
        try (Jedis jedis = handler.get()) {
            System.out.println("释放直接连接redis的资源");
        } catch (Exception e) {
            System.out.println("异常:" + e.getMessage());
        }
    }
}
