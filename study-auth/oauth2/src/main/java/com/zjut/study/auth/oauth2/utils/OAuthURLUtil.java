package com.zjut.study.auth.oauth2.utils;

import com.zjut.common.exception.ServiceException;
import com.zjut.study.auth.oauth2.constants.OAuth2Constant;
import com.zjut.study.auth.oauth2.enums.ThirdPartyEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * 生成
 * @author jack
 */
@Slf4j
public class OAuthURLUtil {
    /**
     * 第三方编码code和引导用户授权链接方法的缓存关系
     */
    private final static Map<String, Function<String, String>> CODE_USER_AUTHORIZE_URL_MAP = new HashMap<>(2);
    // 将不同的获取code接口缓存起来
    static {
        CODE_USER_AUTHORIZE_URL_MAP.put(ThirdPartyEnum.WEIXIN.getCode(), OAuthURLUtil::weixinCodeUrl);
    }

    /**
     * 通过第三方的代码code获取引导用户确认授权并生成临时授权code的链接
     * @param code
     * @return
     */
    public static String getUserAuthorizeURLByThirdPartCode(String code, String state) throws ServiceException {
        Function<String, String> urlFunction = CODE_USER_AUTHORIZE_URL_MAP.get(code);
        if (Objects.isNull(urlFunction)) {
            log.error("查无对应的第三方，请联系管理人员:{}", code);
            throw new ServiceException("查无对应的第三方");
        }
        return urlFunction.apply(state);
    }

    // =========================================================================

    /**
     * 拼接微信的获取临时code的地址
     * @return 获取临时code的地址
     */
    public static String weixinCodeUrl(String state) {
        String codeUrl = null;
        try {
            codeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                    "appid=" + OAuth2Constant.TEST_WEIXIN_APP_ID +
                    "&redirect_uri=" + URLEncoder.encode(OAuth2Constant.TEST_WEIXIN_REDIRECT_URI, "UTF-8") +
                    "&response_type=code" +
                    "&scope=" + OAuth2Constant.WEIXIN_SCOPE_USERINFO +
                    "&state=" + state +
                    "#wechat_redirect";
        } catch (UnsupportedEncodingException e) {
            log.error("拼接获取code地址时编辑跳转地址异常:", e);
        }
        return codeUrl;
    }
}
