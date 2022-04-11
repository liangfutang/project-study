package com.zjut.study.reactor;

import com.zjut.study.common.junit.CommonJunitFilter;
import org.junit.Test;
import reactor.core.publisher.Mono;

public class MonoClient extends CommonJunitFilter {

    @Test
    public void just() {
        Mono<Integer> just = Mono.just(1);
        System.out.println(just);
    }
}
