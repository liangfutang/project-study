package com.zjut.study.mybatis;

import com.zjut.study.common.junit.CommonJunitFilter;
import com.zjut.study.mybatis.entity.Blog;
import com.zjut.study.mybatis.util.Mock;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.BeanWrapper;
import org.apache.ibatis.session.Configuration;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author jack
 */
public class MetaObjectClient extends CommonJunitFilter {

    /**
     * 基本的反射案例
     */
    @Test
    public void reflect() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object blog = new Blog();
        Method setBody = blog.getClass().getDeclaredMethod("setBody", String.class);
        Object invoke = setBody.invoke(blog, "jack");

        Method getBody = blog.getClass().getDeclaredMethod("getBody");
        Object invoke1 = getBody.invoke(blog);
    }

    /**
     * 使用MetaObject做反射
     *
     * // 直接操作属性
     *     // 操作子属性
     *     // 自动创建属性对象
     *     // 自动查找属性名，支持下划线转驼峰
     *     // 基于索引访问数组
     */
    @Test
    public void metaObjectReflect() {
        Object blog = new Blog();

        Configuration configuration = new Configuration();
        MetaObject metaObject = configuration.newMetaObject(blog);
        metaObject.setValue("body", "jack");
        Object body = metaObject.getValue("body");
        System.out.println(body);

        metaObject.setValue("author.name", "houhou");
        Object authorName = metaObject.getValue("author.name");
        System.out.println(authorName);
    }

    @Test
    public void test(){
        // 装饰器模式
        Object blog=  Mock.newBlog();
        Configuration configuration=new Configuration();
        // 装饰
        MetaObject metaObject = configuration.newMetaObject(blog);
        Object value = metaObject.getValue("comments[0].user.name");
        System.out.println(value);
    }

    /**
     * 简单测试PropertyTokenizer分词器
     */
    @Test
    public void test1() {
        // 装饰器模式
        Object blog=  Mock.newBlog();
        Configuration configuration=new Configuration();
        // 装饰
        MetaObject metaObject = configuration.newMetaObject(blog);
        metaObject.getValue("comments[0].user.name");
        BeanWrapper beanWrapper=new BeanWrapper(metaObject,blog);

        beanWrapper.get(new PropertyTokenizer("comments[0]"));
        beanWrapper.get(new PropertyTokenizer("comments"));
    }

    @Test
    public void t() {
        List<String> aa = new ArrayList<>();

        ListIterator<String> stringListIterator = aa.listIterator();
        while (stringListIterator.hasNext()) {
            String next = stringListIterator.next();
        }
    }
}
