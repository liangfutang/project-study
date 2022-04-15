package com.zjut.study.redis;

import com.alibaba.fastjson.JSONObject;
import com.zjut.study.common.junit.CommonJunitFilter;
import com.zjut.study.redis.util.JedisHandlerUtil;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class Redis5Client extends CommonJunitFilter {

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


    @Test
    public void jedisPool() {

    }

}
