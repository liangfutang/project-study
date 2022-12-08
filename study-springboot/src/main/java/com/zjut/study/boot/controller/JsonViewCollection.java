package com.zjut.study.boot.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.zjut.study.boot.vo.UserVO;
import com.zjut.study.common.convention.result.Result;
import com.zjut.study.common.convention.result.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jack
 */
@RestController
@RequestMapping("/jsonview")
@Slf4j
public class JsonViewCollection {

    @GetMapping("/simple/01")
    @JsonView(UserVO.UserSimpleView.class)
    public UserVO simple01() {
        UserVO userVO = new UserVO()
                .setId(12).setName("jack").setAge(13).setNikeName("haha");
        log.info("即将输出的用户数据:{}", userVO);
        return userVO;
    }

    @GetMapping("/simple/02")
    @JsonView(UserVO.UserSimpleView.class)
    public List<UserVO> simple02() {
        UserVO userVO = new UserVO()
                .setId(12).setName("jack").setAge(13).setNikeName("haha");
        List<UserVO> result = new ArrayList<>();
        result.add(userVO);
        log.info("即将输出的用户数据:{}", userVO);
        return result;
    }

    /**
     * 被包装过的使用  @JsonView  无效
     * @return
     */
    @GetMapping("/simple/03")
    @JsonView(UserVO.UserSimpleView.class)
    public Result<UserVO> simple03() {
        UserVO userVO = new UserVO()
                .setId(12).setName("jack").setAge(13).setNikeName("haha");
        log.info("即将输出的用户数据:{}", userVO);
        return Results.success(userVO);
    }

}
