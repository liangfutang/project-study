package com.zjut.study.auth.oauth2.controller;

import com.zjut.study.auth.oauth2.enums.ThirdPartyEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth/2.0")
@Slf4j
public class OAuth2Controller {

    /**
     * 向对应的合作方申请网页授权
     * @param thirdParty
     */
    @GetMapping("/{thirdParty}")
    public void callThirdOAuth(@PathVariable String thirdParty) {
        log.info("使用:{},第三方授权", thirdParty);
        if (!ThirdPartyEnum.checkThirdPartyByCode(thirdParty)) {
            log.warn("无效第三方授权code:{}", thirdParty);

        }
    }

    @GetMapping("/test")
    public String test() {
        return "success";
    }
}
