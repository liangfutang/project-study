package com.zjut.study.mqtt.boot.config.handle;

import org.springframework.messaging.Message;

/**
 * 继承该接口实现对指定topic的订阅消费
 * @author jack
 */
public interface MessageConsumer {
    /**
     * 订阅的topic
     * @return 当前消费者处理的对应逻辑
     */
    String topic();

    /**
     * 处理对应的业务逻辑
     * @param payload 对应的业务数据
     */
    <T> void handle(T payload);

    /**
     *
     * @return true:需要 false:不需要
     */
    default boolean needOriginMessage() {
        return false;
    }

    /**
     * 如果有特殊需求收到的原始Message内容
     * @param message 收到的原始消息内容
     * @return
     */
    default <T> void originMessage(Message<T> message) {}
}
