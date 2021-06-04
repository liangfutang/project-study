package com.zjut.study.patterns.template.demo.handler;

import com.zjut.study.patterns.template.demo.SimcardInfo;
import com.zjut.study.patterns.template.demo.SimcardTemplate;

public class CUCCSIMcard extends SimcardTemplate {
    @Override
    protected SimcardInfo lookForSimcard() {
        return null;
    }

    @Override
    protected void buy(Callback callback) {

    }

    @Override
    protected void orderPackage(Callback callback) {

    }
}
