package com.zjut.study.patterns.template.demo;

import com.zjut.study.patterns.template.demo.handler.CMCCSimcard;
import org.junit.Test;

public class Client {

    @Test
    public void test() {
        SimcardTemplate cmcc = new CMCCSimcard();
        cmcc.buySimcard();
    }
}
