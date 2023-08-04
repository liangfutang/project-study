package com.zjut.study.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.zjut.study.jna.entity.StudentEntity;

/**
 * 库函数记载
 */
public interface SolveService extends Library {
    SolveService INSTANCE = Native.load("/usr/lib/Solve.so", SolveService.class);

    /**
     * a + b = c
     * @param a 入参
     * @param b 入参
     * @return 累计结果
     */
    int addNum(int a, int b);

    /**
     * 简单的对入参进行赋值
     * @param student 传入的学生信息
     */
    void setCallName(StudentEntity.ByReference student);
}
