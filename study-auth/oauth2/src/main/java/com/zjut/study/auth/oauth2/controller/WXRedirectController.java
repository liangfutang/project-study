package com.zjut.study.auth.oauth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信跳转过程或内网穿透时被微信屏蔽的连接申请过程中用到的接口
 * 正常情况下不需要使用到
 */
@RestController
public class WXRedirectController {

    /**
     * JS安全域名配置
     * @return
     */
    @GetMapping("MP_verify_5UZgELHijf7MLuTs.txt")
    public String getRedirect() {
        return "5UZgELHijf7MLuTs";
    }

    /**
     * 内网穿透地址被屏蔽后申请解封用到的接口
     * @return
     */
    @GetMapping("955f43ade344bbd57bab319755d610db.txt")
    public String getRedirect1() {
        return "05d57b872e960c2f94dcd5d58220ccef4cf24cd5";
    }
}
