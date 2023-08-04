package com.zjut.study.common.utils;

import com.zjut.study.common.constants.Constants;

/**
 * 操作系统相关的工具
 */
public class SysUtil {

    private static String OS = System.getProperty(Constants.OS_NAME, Constants.EMPTY_STR);

    /**
     * @return 当前系统
     */
    public static String sysName() {
        String os = OS.toLowerCase();
        if (os.startsWith(Constants.WIN)) {
            return Constants.WINDOWS;
        } else if (os.startsWith(Constants.LINUX)) {
            return Constants.LINUX;
        }
        return Constants.OTHER;
    }
}
