package com.zjut.study.jna.entity;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class BufDataEntity extends Structure {
    public static class ByReference extends BufDataEntity implements Structure.ByReference { }
    public static class ByValue extends BufDataEntity implements Structure.ByValue { }

    public String out_buff;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList( "out_buff");
    }
}
