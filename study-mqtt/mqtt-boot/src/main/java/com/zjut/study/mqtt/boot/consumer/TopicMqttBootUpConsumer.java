package com.zjut.study.mqtt.boot.consumer;

import com.alibaba.fastjson.JSONObject;
import com.zjut.study.mqtt.boot.config.handle.MessageConsumer;
import org.springframework.stereotype.Component;

/**
 * 消费订阅的默认主题
 * @author jack
 */
@Component
public class TopicMqttBootUpConsumer implements MessageConsumer {
    @Override
    public String topic() {
        return "test/hello/world";
    }

    @Override
    public <T> void handle(T payload) {
        JSONObject jsonObject = JSONObject.parseObject(payload.toString());
        System.out.printf("接受到的消息内容:%s%n", jsonObject.toString());
    }
}
