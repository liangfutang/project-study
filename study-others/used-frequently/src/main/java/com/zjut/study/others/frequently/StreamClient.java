package com.zjut.study.others.frequently;

import com.alibaba.fastjson.JSONObject;
import com.zjut.study.common.junit.CommonJunitFilter;
import com.zjut.study.others.frequently.entity.Apple;
import com.zjut.study.others.frequently.entity.Personal;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用来测试常用的流的案例
 * @author jack
 */
public class StreamClient extends CommonJunitFilter {

    @Before
    public void init(){
        apples.add(new Apple("red", 300, "安徽"));
        apples.add(new Apple("red", 300, "湖南"));
        apples.add(new Apple("red", 500, "山东"));
        apples.add(new Apple("green", 600, "辽宁"));
        apples.add(new Apple("green", 700, "甘肃"));
        apples.add(new Apple("blue", 800, "天津"));
        apples.add(new Apple("blue", 900, "云南"));

        appleList.add(new Apple("red", 300, "安徽"));
        appleList.add(new Apple("red", 500, "山东"));
        appleList.add(new Apple("green", 700, "甘肃"));
        appleList.add(new Apple("blue", 900, "云南"));

        Personal oneP = new Personal();
        oneP.setApples(appleList);

        Personal twoP = new Personal();
        twoP.setApples(appleList);

        personals.add(oneP);
        personals.add(twoP);
    }
    private List<Apple> apples = new ArrayList<>();
    private List<Apple> appleList = new ArrayList<>();
    private List<Personal> personals = new ArrayList<>();


    /**
     * 根据颜色分类，并对每组取其中一个属性计算平均值
     */
    @Test
    public void test1() {
        Map<String, Double> color2averg = apples.stream().collect(Collectors.groupingBy(Apple::getColor, Collectors.averagingInt(Apple::getWeight)));
        System.out.println("按颜色分组并计算每组重量结果:" + JSONObject.toJSONString(color2averg));
    }

    /**
     * 将集合中的集合整合成一个集合
     * 和map的区别，map:一对一，返回空会导致list中节点存储的是null， flatMap是一对多的且支持返回空流的
     */
    @Test
    public void flatMap() {
        List<Apple> apples = personals.stream().flatMap(one -> one.getApples().stream().filter(af -> af.getWeight()<0)).collect(Collectors.toList());
        System.out.println("所有人的苹果加起来:" + JSONObject.toJSONString(apples));
        System.out.println("一共:" + apples.size());
    }

    /**
     * 按照某个字段分组，并计算每个组的数量
     */
    @Test
    public void groupAndCount() {
        Map<String, Long> collect = apples.stream().collect(Collectors.groupingBy(Apple::getColor, Collectors.counting()));
        System.out.println(collect);
    }

    /**
     * 按照其中一个字段分组，再按照两一个字段分组
     */
    @Test
    public void groupAndGroupMap() {
        Map<String, Map<Integer, Apple>> collect = apples.stream().collect(Collectors.groupingBy(Apple::getColor, Collectors.toMap(Apple::getWeight, Function.identity(), (k1, k2)->k1)));
        System.out.println(JSONObject.toJSONString(collect));
    }

    /**
     * 按照一个字段分组，按照另一个字段求集合
     */
    @Test
    public void groupAndGroupList() {
        Map<String, List<Integer>> collect = apples.stream()
                .collect(
                        Collectors.groupingBy(Apple::getColor
                                , Collectors.mapping(Apple::getWeight, Collectors.toList())));
        System.out.println(JSONObject.toJSONString(collect));
    }

    @Test
    public void collectingAndThen() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        Double result = list.stream().collect(Collectors.collectingAndThen(Collectors.averagingLong(v -> {
                    System.out.println("v--" + v + "--> " + v * 2);
                    return v * 2;
                }),
                s -> {
                    System.out.println("s--" + s + "--> " + s * s);
                    return s * s;
                }));
        System.out.println(result);
    }
}
