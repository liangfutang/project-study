package com.zjut.study.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.zjut.study.jna.entity.BufDataEntity;


/**
 * 动态链接库中的函数映射
 */
public interface LibgetGSVService extends Library {
    LibgetGSVService INSTANCE = Native.load("/usr/lib/libgetgsv.so", LibgetGSVService.class);

    int get_buff(byte[] buff, double[] xyz, BufDataEntity.ByReference out_buff, int str_len);
}
