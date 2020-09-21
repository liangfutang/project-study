package com.zjut.study.others.java.enums.method;


import org.junit.Test;

public class StartegyTest {

    @Test
    public void testStartegy() {
        Startegy.FAST.run();
    }


    /**
     * switch中支持枚举类型
     */
    @Test
    public void test02() {
        Startegy startegy = Startegy.FAST;
        switch (startegy) {
            case FAST:
                System.out.println("输出快的一腿");
                break;
            case SLOW:
                System.out.println("输出慢出天际");
                break;
            default:
                System.out.println("没有满足的类型值");
        }
    }

}
