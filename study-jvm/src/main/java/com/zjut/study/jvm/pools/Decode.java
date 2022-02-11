package com.zjut.study.jvm.pools;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Decode {
    public static void main(String[] args) throws Exception {
        // 解析token中内容
        String token = "ZjsWdi%2BQKSBFJ9qYKtIqSjd8B14Xrtk2WDdahW%2BCj9jvOy8VgpI542v7MCUy3NJ3CIfKJY%2BInbUcWwydNON%2BiJ00UG5mYChSE%2FpmvJ7lzZbqsTSW3lf4uAngzkZqS8piuGdduZHyUrBBGOhIvUQ5V03xy9EEcYPJ9sPQnnfEiEHEbev8tAgzPgGKMTAb%2BfI9YORNHiZykrol1Ik52gkvgfH1%2BhX8nderojAWW5ZkXAVOhfj%2Ft9qPj23aT2fd0lw%2BOJTHKZUddsnT41gGaA1DHGpIcMKzu9GPr66jzAYtDJyfPbU5c65vq8Rt6%2Fy0CDM%2BAYoxMBv58j1g5E0eJnKSuiXUiTnaCS%2BB8fX6Ffyd16vviGuu%2BG6QcZWFFX7z69HYROtfrIElfXj5Hg8FJt2EqX%2BUc4gFVIYcocNyxSiaUWc%3D";
        // 合作方密钥，由微医分配
        String key = "6e7642dcbae44fe687763c0951fafb24";
        String decryptData = decrypt(URLDecoder.decode(token, "UTF-8"), key);
        System.out.println(decryptData);

        // 解析target中奖领卡和落地页地址
//        String target = "http%3A%2F%2Ftongcheng.vip.guahao-test.com%2Fpartners%2Fstandard%2Freceive%3Ftarget%3Dhttp%253A%252F%252Ftongcheng.vip.guahao-test.com%252Fmember-center";
//        String decodeTarget = URLDecoder.decode(target, "UTF-8");
//        System.out.println(decodeTarget);

//        String luodi = "http://www.baidu.com";
//        String decodeLuodi = URLDecoder.decode(luodi, "UTF-8");
//        System.out.println(decodeLuodi);
//        while (true) {
//            String test = getTest();
//            System.out.println(test);
//            Thread.sleep(1000);
//        }
    }

    private static String getTest() {
        return "aaaaaa";
    }

    private static String decrypt(String encryptData, String secret) {
        try {
            byte[] bytesrc = Base64.getDecoder().decode(encryptData.getBytes());
//            byte[] bytesrc = Base64.decodeBase64(encryptData.getBytes());
            DESedeKeySpec dks = new DESedeKeySpec(secret.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey securekey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, securekey);
            byte[] retByte = cipher.doFinal(bytesrc);
            return new String(retByte);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
