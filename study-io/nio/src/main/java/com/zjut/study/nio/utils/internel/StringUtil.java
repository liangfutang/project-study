package com.zjut.study.nio.utils.internel;

import lombok.extern.slf4j.Slf4j;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * ByteBufferUtil中使用到的了netty的相关工具，这里暂时用来替换，后面重写
 * TODO
 * @author jack
 */
@Slf4j
public class StringUtil {
    public static final String NEWLINE = StringUtil.get("line.separator", "\n");
    private static final String[] BYTE2HEX_PAD = new String[256];
    private static final String[] BYTE2HEX_NOPAD = new String[256];

    static {
        for(int i = 0; i < BYTE2HEX_PAD.length; ++i) {
            String str = Integer.toHexString(i);
            BYTE2HEX_PAD[i] = i > 15 ? str : '0' + str;
            BYTE2HEX_NOPAD[i] = str;
        }

    }

    public static String get(final String key, String def) {
        if (key == null) {
            throw new NullPointerException("key");
        } else if (key.isEmpty()) {
            throw new IllegalArgumentException("key must not be empty.");
        } else {
            String value = null;

            try {
                if (System.getSecurityManager() == null) {
                    value = System.getProperty(key);
                } else {
                    value = (String) AccessController.doPrivileged(new PrivilegedAction<String>() {
                        @Override
                        public String run() {
                            return System.getProperty(key);
                        }
                    });
                }
            } catch (SecurityException var4) {
                log.warn("Unable to retrieve a system property '{}'; default values will be used.", key, var4);
            }

            return value == null ? def : value;
        }
    }

    public static String byteToHexStringPadded(int value) {
        return BYTE2HEX_PAD[value & 255];
    }
}
