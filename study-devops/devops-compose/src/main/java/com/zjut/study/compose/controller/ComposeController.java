package com.zjut.study.compose.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/compose")
public class ComposeController {

    @GetMapping("/test")
    public String test() {
        return "success";
    }
}
