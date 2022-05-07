package com.zjut.study.basic.enums;

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
    },

    DEFAULT {
        @Override
        public void run() {
            System.out.println("default");
        }

        @Override
        public void eat() {
            System.out.println("change default eat");
        }
    };

    abstract public void run();

    public void eat() {
        System.out.println("eat");
    }
}
