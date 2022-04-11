package com.zjut.study.reactor;

import com.zjut.study.common.junit.CommonJunitFilter;
import org.junit.Test;
import reactor.core.publisher.Flux;

public class FluxClient extends CommonJunitFilter {

    @Test
    public void just() {
        Flux<Integer> just = Flux.just(1, 2, 3);
    }
}
