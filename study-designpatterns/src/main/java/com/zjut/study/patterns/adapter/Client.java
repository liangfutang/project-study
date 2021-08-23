package com.zjut.study.patterns.adapter;


import com.zjut.study.patterns.adapter.classadapter.ACAdapter;
import com.zjut.study.patterns.adapter.classadapter.TargetDC;
import com.zjut.study.patterns.adapter.moreadapter.ACAdapter10;
import com.zjut.study.patterns.adapter.moreadapter.ACAdapter5;
import com.zjut.study.patterns.adapter.moreadapter.OriginAC2;
import com.zjut.study.patterns.adapter.moreadapter.TargetDC2;
import com.zjut.study.patterns.adapter.objadapter.ACAdapter1;
import com.zjut.study.patterns.adapter.objadapter.OriginAC1;
import com.zjut.study.patterns.adapter.objadapter.TargetDC1;
import org.junit.Test;

public class Client {

    /**
     * 使用继承的方式实现的电压转换
     */
    @Test
    public void test01() {
        TargetDC one = new ACAdapter();
        System.out.println("电压转换后的输出:" + one.outputDC());
    }

    /**
     * 使用对象的方式转换电压
     */
    @Test
    public void test02() {
        TargetDC1 one= new ACAdapter1(new OriginAC1());
        System.out.println("电压转换后的输出:" + one.outputDC());
    }


    /**
     * 使用中间层转换的方式转换电压
     */
    @Test
    public void test03() {
        OriginAC2 origin = new OriginAC2();
        TargetDC2 one = new ACAdapter5(origin);
        TargetDC2 two = new ACAdapter10(origin);

        System.out.println("5V转化器转换的结果:" + one.outputDC5());
        System.out.println("5V转化器转换的结果:" + two.outputDC10());
    }

}
