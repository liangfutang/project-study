package com.zjut.study.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudyApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudyApplication.class, args);

        // 添加一个钩子程序，在jvm最终关闭之前执行最后的动作
        Runtime.getRuntime().addShutdownHook(new Thread(() ->{
            System.out.println("最终还是钩子抗下了所有");
        }));
    }
}
