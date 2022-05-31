package com.zjut.study.auth.oauth2.service;

/**
 * 签名校验
 * @author jack
 */
public interface SignatureCheckService {
    /**
     * 微信签名校验
     * @param echoStr 如果签名成功，原样传回给服务器
     * @param signature 传入的签名值
     * @param signatureStr 需要计算签名的内容
     * @return 回传的字符串
     */
    String wxSignatureCheck(String echoStr, String signature, String... signatureStr);
}
