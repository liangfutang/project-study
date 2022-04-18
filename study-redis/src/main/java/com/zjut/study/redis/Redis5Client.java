package com.zjut.study.redis;

import com.alibaba.fastjson.JSONObject;
import com.zjut.study.common.junit.CommonJunitFilter;
import com.zjut.study.redis.entity.RedisProperties;
import com.zjut.study.redis.util.JedisHandlerUtil;
import com.zjut.study.redis.util.JedisPoolUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.params.SetParams;

import java.util.stream.IntStream;

public class Redis5Client extends CommonJunitFilter {

    /**
     * 初始化必要的资源
     */
    @Before
    public void init() {
        JedisPoolUtil.initInstance(new RedisProperties());
    }

    @After
    public void after() {
        JedisPoolUtil.closePool();
    }

    /**
     * 使用Jedis直连
     */
    @Test
    public void redirectJedis() {
        JedisHandlerUtil.handler(() -> {

            Jedis jedis = new Jedis("127.0.0.1", 6379);
            jedis.auth("123456");
            jedis.select(2);

            JSONObject value = new JSONObject();
            value.put("jack", "rose");
            System.out.println("放到redis结果: " + jedis.set("redirect", value.toJSONString()));
            System.out.println("从redis中取: " + jedis.get("redirect"));

            return jedis;
        });
    }

    /**
     * 使用连接池
     */
    @Test
    public void jedisPool() {
        JedisHandlerUtil.handler(() -> {

            Jedis jedis = JedisPoolUtil.getResource();
            JSONObject value = new JSONObject();
            value.put("jack", "rose");

            jedis.del("pool");
            SetParams params = SetParams.setParams().nx().px(50000);
            System.out.println("新增一条数据: " + jedis.set("pool", value.toJSONString(), params));
            System.out.println("查询到结果: " + jedis.get("pool"));

            return jedis;
        });
    }

    /**
     * 管道批量操作同一类型操作
     */
    @Test
    public void pipelined() {
        JedisHandlerUtil.handler(() -> {

            Jedis jedis = JedisPoolUtil.getResource();
            Pipeline pipeline = jedis.pipelined();

            IntStream.rangeClosed(1, 6).forEach(i -> {
                JSONObject value = new JSONObject();
                value.put("jack"+i, "rose"+i);
                pipeline.append("pool"+i, value.toJSONString());
            });

            pipeline.sync();
            pipeline.close();
            return jedis;
        });
    }

}
