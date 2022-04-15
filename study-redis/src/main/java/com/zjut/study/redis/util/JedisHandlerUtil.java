package com.zjut.study.redis.util;

import redis.clients.jedis.Jedis;

import java.util.Objects;
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
        Objects.requireNonNull(handler);
        Jedis jedis = null;
        try {
            jedis = handler.get();
        } catch (Exception e) {
            System.out.println("异常:" + e.getMessage());
        } finally {
            // Jedis对close方法进行了改造，如果是连接池中的连接对象，调用Close方法将会是把连接对象返回到对象池，若不是则关闭连接。
            JedisPoolUtil.release(jedis);
            System.out.println("释放jedis资源到连接池");
        }
    }

    /**
     * 简单代理JedisPool测试相关代码
     * @param handler
     */
    public static void handler(Supplier<? extends Jedis> handler) {
        Objects.requireNonNull(handler);
        try (Jedis jedis = handler.get()) {
            System.out.println("释放直接连接redis的资源");
        } catch (Exception e) {
            System.out.println("异常:" + e.getMessage());
        }
    }
}
