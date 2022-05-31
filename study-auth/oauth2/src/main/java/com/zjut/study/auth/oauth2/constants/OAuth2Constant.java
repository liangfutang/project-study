package com.zjut.study.auth.oauth2.constants;

/**
 * 常量
 * @author jack
 */
public interface OAuth2Constant {

    /**
     * 测试微信公众号参数信息
     */
    String WEIXIN_SCOPE_USERINFO = "snsapi_userinfo";
    String WEIXIN_SCOPE_BASE = "snsapi_base";

    String TEST_WEIXIN_AUTH_2_URL = "";
    String TEST_WEIXIN_APP_ID = "wx439517749b154ae1";
    String TEST_WEIXIN_APP_SECRET = "3574090303cfea0ea4e2afe04502f70e";
    String TEST_WEIXIN_REDIRECT_URI = "http://fujing.free.svipss.top/oauth/2.0/invoke";
    // 校验接口配置的token
    String TEST_WEIXIN_TOKEN = "weixin_signature";
}
