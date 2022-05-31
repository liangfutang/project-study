package com.zjut.study.auth.oauth2.service.Impl;

import com.zjut.study.auth.oauth2.service.SignatureCheckService;
import com.zjut.study.common.utils.EncryptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

/**
 * 签名校验
 * @author jack
 */
@Service
@Slf4j
public class SignatureCheckServiceImpl implements SignatureCheckService {

    @Override
    public String wxSignatureCheck(String echoStr, String signature, String... signatureStr) {
        if (StringUtils.isBlank(echoStr) || StringUtils.isBlank(signature)
                || signatureStr==null || signatureStr.length==0) {
            return null;
        }
        // 拼接并计算签名值
        Arrays.sort(signatureStr);
        StringBuilder sortString = new StringBuilder();
        for (String str : signatureStr) {
            sortString.append(str);
        }
        String mySignature = EncryptionUtil.SHA1(sortString.toString());
        log.info("传入的签名:{},计算出的签名:{}", signature, mySignature);

        // 校验签名值
        if (StringUtils.isNotBlank(mySignature) && Objects.equals(mySignature, signature)) {
            return echoStr;
        }
        return null;
    }
}
