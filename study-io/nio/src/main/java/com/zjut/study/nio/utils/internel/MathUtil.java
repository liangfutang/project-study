package com.zjut.study.nio.utils.internel;

/**
 * ByteBufferUtil中使用到的了netty的相关工具，这里暂时用来替换，后面重写
 * TODO
 * @author jack
 */
public final class MathUtil {

    private MathUtil() {
    }

    public static int findNextPositivePowerOfTwo(int value) {
        assert value > -2147483648 && value < 1073741824;

        return 1 << 32 - Integer.numberOfLeadingZeros(value - 1);
    }

    public static int safeFindNextPositivePowerOfTwo(int value) {
        return value <= 0 ? 1 : (value >= 1073741824 ? 1073741824 : findNextPositivePowerOfTwo(value));
    }

    public static boolean isOutOfBounds(int index, int length, int capacity) {
        return (index | length | index + length | capacity - (index + length)) < 0;
    }

    public static int compare(int x, int y) {
        return x < y ? -1 : (x > y ? 1 : 0);
    }

    public static int compare(long x, long y) {
        return x < y ? -1 : (x > y ? 1 : 0);
    }

}
