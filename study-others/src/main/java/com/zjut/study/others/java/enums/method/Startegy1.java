package com.zjut.study.others.java.enums.method;

public enum Startegy1 {

    FAST(0) {
        @Override
        public void run() {
            System.out.println("最最快速");
        }
    },
    SLOW(0) {
        @Override
        public void run() {
            System.out.println("最最最慢速");
        }
    };

    private Integer status;
    private Startegy1(Integer status) {
        this.status = status;
    }

    abstract public void run();
}
