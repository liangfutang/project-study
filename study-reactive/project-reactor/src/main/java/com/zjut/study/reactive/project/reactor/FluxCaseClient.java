package com.zjut.study.reactive.project.reactor;

import com.alibaba.fastjson.JSONObject;
import com.zjut.study.common.junit.CommonJunitFilter;
import com.zjut.study.reactive.project.data.BookMemberyData;
import com.zjut.study.reactive.project.entity.Book;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Flux 的一些案例
 */
public class FluxCaseClient extends CommonJunitFilter {

    //==========================找出每中类别中最贵的书===========================

    /**
     * 使用Java stream流的方式查出所有类别中最贵的
     */
    @Test
    public void streamExpensive() {
        List<Object> collect = BookMemberyData.books.stream().collect(Collectors.groupingBy(Book::getCategory))
                .values()
                .stream()
                .map(books ->
                        books.stream().max(Comparator.comparing(Book::getPrice))
                        .get())
                .collect(Collectors.toList());
        System.out.println("所有类别书籍中最贵的分别是:" + JSONObject.toJSONString(collect));
    }

    /**
     * 使用 flux 的方式查出所有类别中最贵的
     */
    @Test
    public void fluxExpensive() {
        Flux<Book> pipeline = this.getExpensiveByCategoryReactive(Flux.just(BookMemberyData.books.stream().toArray(Book[]::new)));
        // 对筛选出的结果处理
        pipeline = pipeline.doOnNext(System.out::println);
        System.out.println("等待pipeline的发生");
        pipeline.subscribe();
    }

    private Flux<Book> getExpensiveByCategoryReactive(Flux<Book> books) {
        return books.collectMultimap(Book::getCategory)
                .flatMapMany(m -> Flux.fromIterable(m.entrySet()))
                .flatMap(e -> Flux.fromIterable(e.getValue())
                        .sort(Comparator.comparing(Book::getPrice).reversed())
                        .next());
    }
}
