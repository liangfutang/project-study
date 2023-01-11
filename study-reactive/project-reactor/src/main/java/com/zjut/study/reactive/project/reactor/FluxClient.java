package com.zjut.study.reactive.project.reactor;

import com.zjut.study.common.junit.CommonJunitFilter;
import org.junit.Test;
import reactor.core.publisher.Flux;

public class FluxClient extends CommonJunitFilter {

    @Test
    public void just() {
        Flux.just(1, 2, 3).subscribe(System.out::println);
    }

    @Test
    public void generate() {
        Flux.generate(() -> 0, (i,sink) -> {
            sink.next("2*" + i + "=" + 2*i);
            if (9==i) {
                sink.complete();
            }
            return ++i;
        }).subscribe(System.out::println);
    }

    /**
     * 对输入的字符串，按照顺序输出字母
     * 输入: hello guys i am bole welcome to beijing school jdk quick fox prizev
     * 输出: abc.....xyz
     */
    @Test
    public void fromArray() {
        Flux.fromArray("hello guys i am bole welcome to beijing school jdk quick fox prizev".split(" "))
                .flatMap(i -> Flux.fromArray(i.split("")))
                .distinct()
                .sort()
                .subscribe(System.out::print);
    }
}
