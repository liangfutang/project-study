package com.zjut.study.jvm.pools;

import com.alibaba.fastjson.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Encode {

    public static void main(String[] args) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("ext_user_id", "TCzltest20220120001");
        map.put("mobile", "1642669361000");
        map.put("target", URLEncoder.encode("https://tongcheng.vip.guahao.com/partners/standard/receive?target=https%3A%2F%2Ftongcheng.vip.guahao.com%2Fmember-center", "UTF-8"));
        map.put("timestamp", "1604141073285");
        map.put("idcard", "330702198510226012");
        map.put("tagCode", "tongcheng");
        String ori = JSONObject.toJSONString(map);
        System.out.println(ori);
        // 合作方密钥，由微医分配
        String key = "7427e0d4e5464d39a34a9326e4ca01df";
        String result = URLEncoder.encode(encrypt(ori, key), "UTF-8");
        System.out.println(result);
    }


    /**
     * DES 加密
     *
     * @param originData 原始数据,未加密
     * @param secret 加密秘钥
     * @return
     */
    public static String encrypt(String originData, String secret) {
        try {
            DESedeKeySpec dks = new
                    DESedeKeySpec(secret.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey securekey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, securekey);
            byte[] b = cipher.doFinal(originData.getBytes());
            // org.apache.commons.codec.binary.Base64
            return new String(Base64.getEncoder().encode(b), StandardCharsets.UTF_8).replaceAll("\r",
                    "").replaceAll("\n", "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
