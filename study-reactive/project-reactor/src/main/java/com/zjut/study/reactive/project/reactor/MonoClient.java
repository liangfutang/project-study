package com.zjut.study.reactive.project.reactor;

import com.zjut.study.common.junit.CommonJunitFilter;
import org.junit.Test;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;
import java.util.Random;

/**
 * Flux 和 Mono 是 Reactor 中的两个基本概念。
 * Flux 表示的是包含 0 到 N 个元素的异步序列。
 * 在该序列中可以包含三种不同类型的消息通知：正常的包含元素的消息、序列结束的消息、序列出错的消息。
 * 当消息通知产生时，订阅者中对应的方法onNext(), onComplete()和 onError()会被调用。
 * Mono 表示的是包含 0 或者 1 个元素的异步序列。该序列中同样可以包含与 Flux 相同的三种类型的消息通知。
 * Flux 和 Mono 之间可以进行转换。对一个 Flux 序列进行计数操作，得到的结果是一个 Mono<Long>对象。把两个 Mono 序列合并在一起，得到的是一个 Flux 对象。
 */
public class MonoClient extends CommonJunitFilter {

    @Test
    public void emptyJust() {
        Mono.empty().subscribe(System.out::println);

        System.out.println("物理隔开");

        Mono.just("jack").subscribe(System.out::println);

        System.out.println("物理隔开");

        Mono.just(1).subscribe(System.out::println);
        // ================================================
        System.out.println("物理隔开");
        MonoClient monoClient = new MonoClient();
        // just里面必须有返回
        Mono.just(monoClient.doSomething1("rose")).subscribe(System.out::println);
        // 返回为空的话会报空指针异常
        Mono.just(monoClient.doSomething1("null")).subscribe(System.out::println);
    }
    public String doSomething1(String name) {
        System.out.printf("do了something:%s,返回%s%n", name, name);
        if ("null".equals(name)) {
            return null;
        } else if ("throw".equals(name)) {
            throw new RuntimeException("");
        }
        return name;
    }

    @Test
    public void justOrEmpty() {
        MonoClient monoClient = new MonoClient();
        Mono.justOrEmpty(monoClient.doSomething1("jack")).subscribe(System.out::println);
        System.out.println("\n===========================\n");
        // 返回为null不会报异常，subscribe不会有动作
        Mono.justOrEmpty(monoClient.doSomething1("null")).subscribe(System.out::println);
        Mono.justOrEmpty(Optional.ofNullable(monoClient.doSomething1("null"))).subscribe(System.out::println);
    }

    /**
     * 创建一个只包含错误消息的序列
     */
    @Test
    public void error() {
        Mono.error(new RuntimeException("error test")).subscribe(System.out::println, System.err::println);
    }

    /**
     * 创建一个不包含任何消息通知的序列
     */
    @Test
    public void never() {
        Mono.never().subscribe(System.out::println);
    }

    @Test
    public void fromRunnable() {
        MonoClient monoClient = new MonoClient();
        Mono.fromRunnable(() -> monoClient.doSomething1("jack")).subscribe(System.out::println, System.err::println);
        Mono.fromRunnable(() -> monoClient.doSomething1("null")).subscribe(System.out::println, System.err::println);
        Mono.fromRunnable(() -> monoClient.doSomething1("throw")).subscribe(System.out::println, System.err::println);
    }

    /**
     * 延时延的不是主线程
     */
    @Test
    public void delay() {
        long start = System.currentTimeMillis();
        Disposable disposable = Mono.delay(Duration.ofSeconds(2)).subscribe(n -> {
            System.out.println("生产数据源:" + n);
            System.out.println("当前线程:" + Thread.currentThread().getName() + ",从生产到消耗用时:" + (System.currentTimeMillis() - start));
        });
        System.out.println("主线程"+ Thread.currentThread().getName() + "耗时："+ (System.currentTimeMillis() - start));

        while (!disposable.isDisposed()){}
    }

    /**
     * 通过 create()方法来使用 MonoSink 来创建 Mono。
     */
    @Test
    public void create() {
        Mono.create(sink -> {
            Random random = new Random();
            if (2==random.nextInt(3)) {
                sink.success("success");
            } else {
                sink.error(new RuntimeException("fail"));
            }
        }).subscribe(System.out::println, System.err::println);
    }
}
