package com.zjut.study.auth.oauth2.controller;

import com.alibaba.fastjson.JSONObject;
import com.zjut.common.exception.ParameterException;
import com.zjut.common.utils.HttpClientUtil;
import com.zjut.study.auth.oauth2.constants.OAuth2Constant;
import com.zjut.study.auth.oauth2.enums.ThirdPartyEnum;
import com.zjut.study.auth.oauth2.service.SignatureCheckService;
import com.zjut.study.auth.oauth2.utils.OAuthURLUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * 微信公众号网页授权文档  https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html#1
 * @author jack
 */
@RestController
@RequestMapping("/oauth/2.0")
@Slf4j
public class OAuth2Controller {

    @Resource
    private SignatureCheckService signatureCheckService;

    /**
     * 向对应的合作方提供的申请网页授权页面跳转，以便用户授权
     * 文档地址  https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html
     * @param thirdParty
     */
    @GetMapping("/call/{thirdParty}")
    public void callThirdOAuth(@PathVariable String thirdParty, HttpServletResponse response) throws IOException {
        log.info("使用:{},第三方授权", thirdParty);
        if (!ThirdPartyEnum.checkThirdPartyByCode(thirdParty)) {
            log.warn("无效第三方授权code:{}", thirdParty);
            throw new ParameterException("当前授权的第三方不存在");
        }

        // 引导用到跳转到确认授权界面并生成临时code
        response.sendRedirect(OAuthURLUtil.getUserAuthorizeURLByThirdPartCode(thirdParty, "STATE"));
    }

    /**
     * 接受code并获取access_token
     * @return
     */
    @GetMapping(path = "invoke")
    public void invoke(@RequestParam String code, @RequestParam String state, HttpServletResponse response) throws IOException, URISyntaxException {
        log.info("进来了code:{},state:{}", code, state);
        if (StringUtils.isBlank(code)) {
            log.error("获取临时code为空");
            throw new ParameterException("获取临时code为空");
        }

        // 获取token
        String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + OAuth2Constant.TEST_WEIXIN_APP_ID +
                "&secret=" + OAuth2Constant.TEST_WEIXIN_APP_SECRET +
                "&code=" + code +
                "&grant_type=authorization_code";
        String tokenResult = HttpClientUtil.getHttpGet(accessTokenUrl, new HashMap<>(), new HashMap<>(), 5000, 5000, 5000);
        log.info("从微信公众号平台获取到token信息:{}", tokenResult);
        // 获取用户信息
        JSONObject tokenJson = JSONObject.parseObject(tokenResult);
        String accessToken = tokenJson.getString("access_token");
        String openid = tokenJson.getString("openid");

        String userinfoUrl = "https://api.weixin.qq.com/sns/userinfo?" +
                "access_token=" + accessToken +
                "&openid=" + openid +
                "&lang=zh_CN";
        String userinfoResult = HttpClientUtil.getHttpGet(userinfoUrl, new HashMap<>(), new HashMap<>(), 5000, 5000, 5000);
        log.info("从微信公众号平台获取到用户信息:{}", userinfoResult);
        JSONObject userinfoJson = JSONObject.parseObject(userinfoResult);

        // 跳转到最终页面
        response.sendRedirect("https://www.baidu.com");
    }

    /**
     * 公众号授权平台和服务器间的签名校验
     * 接口配置信息  https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index
     * 签名值计算    https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Access_Overview.html
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr 随机字符串，若确认此次 GET 请求来自微信服务器，请原样返回 echostr 参数内容，则接入生效，成为开发者成功，否则接入失败
     * @return
     */
    @GetMapping("/signature/check")
    public String test(@RequestParam(value = "signature") String signature,
                       @RequestParam(value = "timestamp") String timestamp,
                       @RequestParam(value = "nonce") String nonce,
                       @RequestParam(value = "echostr") String echostr) {
        log.info("收到微信服务器的请求,signaturesignature:{},timestamp:{},nonce:{},echostr:{}", signature, timestamp, nonce, echostr);
        return signatureCheckService.wxSignatureCheck(echostr, signature, timestamp, nonce, OAuth2Constant.TEST_WEIXIN_TOKEN);
    }

}
