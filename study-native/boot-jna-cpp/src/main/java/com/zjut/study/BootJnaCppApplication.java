package com.zjut.study;

import com.zjut.study.common.utils.SysUtil;
import com.zjut.study.constants.JnaConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootJnaCppApplication {

    public static void main(String[] args) {
        System.setProperty(JnaConstant.JNA_OS_NAME, SysUtil.sysName());
        SpringApplication.run(BootJnaCppApplication.class, args);
    }

}
