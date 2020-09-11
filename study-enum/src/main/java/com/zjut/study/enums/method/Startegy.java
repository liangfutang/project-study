package com.zjut.study.enums.method;

public enum Startegy {

    FAST {
        @Override
        public void run() {
            System.out.println("最快");
        }
    },

    SLOW {
        @Override
        public void run() {
            System.out.println("最慢");
        }
    };

    abstract public void run();
}
