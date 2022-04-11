package com.zjut.study.studywebflux.controller;

import com.zjut.study.common.utils.SmallThreadTool;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 简单是使用Mono和Flux案例
 */
@RestController
public class SimpleTestController {

    /**
     * 简单的使用 Mono
     * @return
     */
    @GetMapping("/mono")
    public Mono<String> mono() {
        long start = System.currentTimeMillis();
        Mono<String> hello = Mono.fromSupplier(() -> {
            SmallThreadTool.sleep(2000);
            return "hello";
        });
        SmallThreadTool.printTimeAndThread("接口耗时:" + (System.currentTimeMillis() - start));
        return hello;
    }

    @GetMapping(value = "/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> flux() {
        Flux<String> flux = Flux.fromArray(new String[]{"张三", "李四", "王五", "李六"}).map(one -> {
            SmallThreadTool.sleep(2000);
            return "二班: " + one;
        });
        return flux;
    }
}
