package com.zjut.study.boot.validate.validation.valueable;

/**
 * 参数校验的数值包装
 * 如果直接使用int数组的话会导致内部向Object数组转换的时候出现转换异常，所以这里对数组进行一次包装
 * @author jack
 */
public interface IntArrayValuable {

    /**
     * @return 有效的参数数组
     */
    int[] array();
}
