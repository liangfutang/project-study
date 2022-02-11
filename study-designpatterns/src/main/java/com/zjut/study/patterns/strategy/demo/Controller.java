package com.zjut.study.patterns.strategy.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 模拟controller层的调用
 */
public class Controller {
    public static void main(String[] args) {
        QueryGrantTypeService queryGrantTypeService = new QueryGrantTypeService(new GrantTypeService());
        System.out.println(queryGrantTypeService.getResult("不存在的优惠方式"));
        System.out.println(queryGrantTypeService.getResult("红包"));
    }
}

/**
 * 中间service层
 */
class QueryGrantTypeService {

    /**
     * 提供给controller调用的方法
     * @param resourceType
     * @return
     */
    public String getResult(String resourceType) {
        Function<String, String> resultFunction = grantTypeMap.get(resourceType);
        if (resultFunction != null) {
            return resultFunction.apply("对应业务资源id");
        }
        return "查询不到该优惠券的发放方式";
    }


    /**
     * 底层具体的业务逻辑
     */
    private GrantTypeService grantTypeService;
    public QueryGrantTypeService(GrantTypeService grantTypeService) {
        this.grantTypeService = grantTypeService;
    }

    /**
     * 存储对应的策略键值关系，使用到的时候调用
     */
    private Map<String, Function<String,String>> grantTypeMap = new HashMap<>();
    {
        grantTypeMap.put("红包",resourceId->grantTypeService.redPaper(resourceId));
        grantTypeMap.put("购物券",resourceId->grantTypeService.shopping(resourceId));
        grantTypeMap.put("qq会员",resourceId->grantTypeService.QQVip(resourceId));
    }
}

/**
 * 底层具体的实现逻辑
 * 如果每个方法内部的逻辑比较复杂可以抽出来一个类
 */
class GrantTypeService {

    public String redPaper(String resourceId){
        //红包的发放方式
        return "每周末9点发放";
    }

    public String shopping(String resourceId){
        //购物券的发放方式
        return "每周三9点发放";
    }

    public String QQVip(String resourceId){
        //qq会员的发放方式
        return "每周一0点开始秒杀";
    }
}
