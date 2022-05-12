package com.zjut.study.jvm.constantpool_stringtable;

import com.zjut.study.common.junit.CommonJunitFilter;
import org.junit.Test;

/**
 * 字符串常量池
 */
public class StringTableClient extends CommonJunitFilter {

    @Test
    public void test01() {
        System.out.println("hello world");
        String s1 = "a";
        String s2 = "b";
        String s3 = "ab";
        String s4 = s1 + s2;
        System.out.println(s3 == s4);
        String s5 = "a" + "b";
        System.out.println(s3 == s5);
    }
}
