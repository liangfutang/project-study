package com.zjut.study.others.cipher.encryption.rsa;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jack
 */
public class RsaServiceImpl {

    public String data = "hello world";
    public String publicKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCISLP98M/56HexX/9FDM8iuIEQozy6kn2JMcbZS5/BhJ+U4PZIChJfggYlWnd8NWn4BYr2kxxyO8Qgvc8rpRZCkN0OSLqLgZGmNvoSlDw80UXq90ZsVHDTOHuSFHw8Bv//B4evUNJBB8g9tpVxr6P5EJ6FMoR/kY2dVFQCQM4+5QIDAQAB";
    public String privateKeyString = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIhIs/3wz/nod7Ff/0UMzyK4gRCjPLqSfYkxxtlLn8GEn5Tg9kgKEl+CBiVad3w1afgFivaTHHI7xCC9zyulFkKQ3Q5IuouBkaY2+hKUPDzRRer3RmxUcNM4e5IUfDwG//8Hh69Q0kEHyD22lXGvo/kQnoUyhH+RjZ1UVAJAzj7lAgMBAAECgYAVh26vsggY0Yl/Asw/qztZn837w93HF3cvYiaokxLErl/LVBJz5OtsHQ09f2IaxBFedfmy5CB9R0W/aly851JxrI8WAkx2W2FNllzhha01fmlNlOSumoiRF++JcbsAjDcrcIiR8eSVNuB6ymBCrx/FqhdX3+t/VUbSAFXYT9tsgQJBALsHurnovZS1qjCTl6pkNS0V5qio88SzYP7lzgq0eYGlvfupdlLX8/MrSdi4DherMTcutUcaTzgQU20uAI0EMyECQQC6il1Kdkw8Peeb0JZMHbs+cMCsbGATiAt4pfo1b/i9/BO0QnRgDqYcjt3J9Ux22dPYbDpDtMjMRNrAKFb4BJdFAkBMrdWTZOVc88IL2mcC98SJcII5wdL3YSeyOZto7icmzUH/zLFzM5CTsLq8/HDiqVArNJ4jwZia/q6Fg6e8KO2hAkB0EK1VLF/ox7e5GkK533Hmuu8XGWN6I5bHnbYd06qYQyTbbtHMBrFSaY4UH91Qwd3u9gAWqoCZoGnfT/o03V5lAkBqq8jZd2lHifey+9cf1hsHD5WQbjJKPPIb57CK08hn7vUlX5ePJ02Q8AhdZKETaW+EsqJWpNgsu5wPqsy2UynO";
    public String privateKeyString1 = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAITMAXbQvIDZanrwQmh5uE7r09z74FWWBe0o2ojbwcEiTuOWyUstU6ZLnwV4/3wsyOK2cg2RUYH6eLBd8nGH88Ww8vnzgr7hj4UAxziKCA1w0gUhDx/8kzbisl7i3r7rgygcK42nZ7APXE5gnFurcOZn5ZsCVu1vy9voi2J9HQcxAgMBAAECgYA7SxOER6NDLNB8X/Zmy6JdqrKIQvZhgIDY6ZrZ7LVQVM9Akz0BkhV9dNpmCSaZ/S0c/TVjlXT9QibNKMVtp7RR8vODPOTJeqQVWnTmd6R2+umc1gCoK4x11Mt7TKJgniUQwWBRJDLXeIs1uAvYjVScoxEFn+sb0ZARqDDvoE+DgQJBAN/ama45/IIGBqi8Lc+ru0PGbEOW1tGuwe5xlWCm37iTiSN8ufd5Zqoj399Cej1RFiF3yWXvNvlz4GXCIZUJbnkCQQCX3e8hgJ/Nzr9A3/BAB8CJHB3Yrhr5MfT4KTNClYpVX4HMxfiVhyBwqFc9myysxWyCbfPlpDzMkgJ48pVRslB5AkBt4tYr44UjoqekLvPiDLAZfg7pQuNi/QFv9kvYrTIHXk0koXZ8mBigjom4A98TuHk/ppiztDDtVUejTB/GFGIpAkA0mYbYuL8gvmoZozaIRucV1io0QWFQTFBHn6WEe1z1YuYHMc5+o9XFj1jgGp3nnV2O81xVfoQcarn2gDbdnAApAkEAkyqJPKI6ffPOnqeO7DO/m9XnLNWZ2Q2SvtERV5bmMPB82CsGM5Qg5srJqz5oUFgxzWLHwMgfsDGVhWhDG13h2A==";


    public static void main(String[] args) throws Exception {

        RsaServiceImpl rsaService = new RsaServiceImpl();

        //获取公钥
        PublicKey publicKey = rsaService.getPublicKey(rsaService.publicKeyString);
        System.out.println("公钥:" + publicKey);
        //获取私钥
        PrivateKey privateKey = rsaService.getPrivateKey(rsaService.privateKeyString1);
        System.out.println("私钥:" + privateKey);
        //公钥加密
        byte[] encryptedBytes = rsaService.encrypt(rsaService.data.getBytes("utf-8"), publicKey);
        System.out.println("加密后：" + new String(encryptedBytes, "utf-8"));

        //私钥解密

        String password = "Qv5DcIeGu92i3T2iqSEKgBLw2OX0IeNKY3qYXB/fHzJGuTjpKcYWgq7PLuKp0aXc3/QKicobiJr1QsjmLKm1rFFfJOwEuiCzFXX68pq10Jv7n6U+4rx1iB4FkEElPrkmyOvx58YiiBeLd+RMqB28QA4Hm+Gs4zv4nlhBrF6WnmoydXF68bx7ELwPeSTWIS5E3eK+ZtJn8zkJjo/mT9vi+VgQEep3Oxsq9aDz3lgbbDRLXp369NX9aVE32heT4Tjvh0ITqPMDjMFpALO1+CgbJ5ykk7ZCOv/Bjc9h+4YnLLpiCAKmUsWaRQxnQAZAY6qK/y0FdJwUxRZ/03Xc/i1dFirO4HFFHK+1RxpZo+DYU5VhlvEdWDpjUmOd0MN+NUwZiNJSZKbJuwy3F4GAu605gTpQ6Qm6wihuieo6bRdo6z5Rv3HzxCFfv9RWpfZi+D2nNtF1wacPGkGPylEeEcKbdJ2M7hQsG4OyCJEk8Ea8X+DuWMNiWz9wJ6JS/zHrJjcoVbdHR1iKqbgUBd7qIIAuQvAU7va/1c7cJTMI5i8K78CygcRXkGqldrqqVtdOGnpf+5ATO7aymWxwoEW1XdnmIRV1bewv18DuU2lirVRPPSKjCTEvMiadkM0vJCK+dlU+ODhKM65wzKzTmyvtYlHgsfaOdu74G1OT8YFUNB8NvuE=";

        byte[] password2 = Base64.getDecoder().decode(password.getBytes("utf-8"));


        byte[] decryptedBytes = rsaService.decrypt(password2, privateKey);
        System.out.println("解密后：" + new String(decryptedBytes));

    }

    /**
     * 将base64编码后的公钥字符串转成PublicKey实例
     *
     * @param publicKey
     * @return
     * @throws Exception
     */
    public PublicKey getPublicKey(String publicKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKey.getBytes());
        return getPublicKey(keyBytes);
    }

    /**
     * 将base64编码后的私钥字符串转成PrivateKey实例
     *
     * @param privateKey
     * @return
     * @throws Exception
     */
    public PrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKey.getBytes());
        return getPrivateKey(keyBytes);
    }

    /**
     * 公钥加密
     *
     * @param content
     * @param publicKey
     * @return
     * @throws Exception
     */
    public byte[] encrypt(byte[] content, PublicKey publicKey) throws Exception {
        //java默认"RSA"="RSA/ECB/PKCS1Padding"
        Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(content);
    }

    /**
     * 私钥解密
     *
     * @param content
     * @param privateKey
     * @return
     * @throws Exception
     */
    public byte[] decrypt(byte[] content, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(content);
    }

    public String encrypt(String content, Key encKey) throws Exception {
        byte[] srcBytes = content.getBytes();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, encKey);
        byte[] resultBytes = cipher.doFinal(srcBytes);
        return Base64.getEncoder().encodeToString(resultBytes);
    }

    public String decrypt(String content, Key decKey) throws Exception {
        byte[] srcBytes = Base64.getDecoder().decode(content);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, decKey);
        byte[] resultBytes = cipher.doFinal(srcBytes);
        return new String(resultBytes);
    }

    /**
     * 将base64编码后的公钥字符串转成PublicKey实例
     *
     * @param publicKey
     * @return
     * @throws Exception
     */
    public PublicKey getPublicKey(byte[] publicKey) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 将base64编码后的私钥字符串转成PrivateKey实例
     *
     * @param privateKey
     * @return
     * @throws Exception
     */
    public PrivateKey getPrivateKey(byte[] privateKey) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public Map<String, String> genKeyPair(int size) throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");

        keyPairGen.initialize(size, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        return new HashMap<String, String>(16) {{
            put("privateKey", privateKeyString);
            put("publicKey", publicKeyString);
        }};
    }

}


