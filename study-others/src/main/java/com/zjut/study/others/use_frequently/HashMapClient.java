package com.zjut.study.others.use_frequently;

import com.alibaba.fastjson.JSONObject;
import com.zjut.study.common.junit.CommonJunitFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * HashMap常用的一些容易忘记的方法
 * @author jack
 */
public class HashMapClient extends CommonJunitFilter {

    /**
     * 初始化相关容器
     */
    @Before
    public void init() {
        map.put("jack", 6);
        map.put("rose", 7);

        System.out.println("初始容器中的数据:" + JSONObject.toJSONString(map) + "\n");
    }

    @After
    public void after() {
        System.out.println("\n计算后容器中:" + JSONObject.toJSONString(map));
    }

    Map<String, Integer> map = new HashMap<>();

    /**
     * 无论是否存在都计算
     *
     * 1. 查出key对应的Node
     * 2. 无论 找着 还是 找不着，返回 null 则删除该节点 或 不保存计算后的该节点
     *                    返回 非null，保存计算后的节点 或 新增该新节点
     */
    @Test
    public void compute() {

        // 当前容器中已经存在的
        map.compute("jack", (k,v) -> {
            System.out.printf("当前的k %s v %s%n", k, v);
            // 如果返回null，该节点也会拿掉
            return v+1;
        });

        // 当前容器中不存在的
        map.compute("node", (k,v) -> {
            System.out.printf("当前的k %s v %s%n", k, v);
            // 返回null表示该不存在的节点将不会存储到容器中，否则会作为一个新的节点存储到容器中
            return null;
        });

    }

    /**
     * 如果存在则计算
     *
     * 找出当前容器中存在节点做计算，并将计算后的value返回出来
     * 如果返回null将会删除或不保存该节点
     */
    @Test
    public void computeIfPresent() {
        // 当前容器中已经存在的
        Integer jack = map.computeIfPresent("jack", (k, v) -> {
            System.out.printf("当前的k %s v %s%n", k, v);
            // 返回null将会删除该节点
            return v + 2;
        });
        System.out.println("容器中存在的计算结果:" + jack);

        Integer node = map.computeIfPresent("node", (k, v) -> {
            // 当前容器中不存在，不比执行该计算方法
            System.out.printf("当前的k %s v %s%n", k, v);
            return 2;
        });
        System.out.println("容器中不存在的计算结果:" + node);
    }

    /**
     * 当前容器不存在则计算
     *
     * 判断当前容器中是否存在该节点，不存在则计算后插入，存在则不做计算操作
     */
    @Test
    public void c() {
        // 当前容器中是有的
        Integer jack = map.computeIfAbsent("jack", (k) -> {
            System.out.printf("当前的k %s", k);
            return 2;
        });
        System.out.println("容器中存在的计算结果:" + jack);

        // 当前容器中没有的
        Integer node = map.computeIfAbsent("node", k -> {
            // 当前容器中不存在该节点，所以会执行该计算再将节点放到容器中
            System.out.printf("当前的k %s%n", k);
            return 2;
        });
        System.out.println("容器中不存在的计算结果:" + node);
    }

    /**
     * 将新的一对(k,v) merge到当前容器中
     *
     * 如果根据key在当前容器中能找到，则将传入的value和就的value合并计算后以新的value存入
     * 如找不到，则以计算后新的节点存入
     */
    @Test
    public void merge() {
        Integer jack = map.merge("jack", 2, (ov, v) -> {
            System.out.printf("当前的k %s v %s%n", ov, v);
            // 如果返回 null 则相当于执行remove方法
            return ov + v;
        });
        System.out.println("容器中存在的计算结果:" + jack);

        Integer node = map.merge("node", 2, (ov, v) -> {
            System.out.printf("当前的k %s v %s%n", ov, v);
            return ov + v;
        });
        System.out.println("容器中不存在的计算结果:" + node);
    }
}

