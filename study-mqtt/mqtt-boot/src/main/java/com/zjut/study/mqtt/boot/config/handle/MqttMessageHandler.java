package com.zjut.study.mqtt.boot.config.handle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * 处理分发订阅进来的消息
 * @author jack
 */
@Slf4j
public class MqttMessageHandler implements MessageHandler {

    /**
     * 订阅的topic和对应的消费处理对应逻辑关系
     */
    private final Map<String, MessageConsumer> messageConsumerMap = new HashMap<>();

    public MqttMessageHandler(List<MessageConsumer> messageConsumerList) {
        this.init(messageConsumerList);
    }

    /**
     * 处理服务器收到的所有消息，这里主要做消息的分发处理
     * @param message 收到的消息
     * @throws MessagingException
     */
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        Object topicObj = message.getHeaders().get("mqtt_receivedTopic");
        if (Objects.isNull(topicObj)) {
            log.error("消息体错误");
            return;
        }
        // 处理对应的消息
        MessageConsumer messageConsumer = messageConsumerMap.get(topicObj.toString());
        if (Objects.isNull(messageConsumer)) {
            log.error("topic:{} 找不到对应的处理器", topicObj);
            return;
        }
        if (messageConsumer.needOriginMessage()) {
            messageConsumer.originMessage(message);
        } else {
            messageConsumer.handle(message.getPayload());
        }
    }


    /**
     * 做初始化相关操作
     * @param messageConsumerList 所有处理订阅消息的消费者
     */
    private void init(List<MessageConsumer> messageConsumerList) {
        messageConsumerList = Optional.ofNullable(messageConsumerList).orElseGet(ArrayList::new);
        // 暂时不考虑监听通配的处理方式，默认topic是最终发送的topic
        // 后期如果想通配处理不同的topic消息，这里修改映射关系
        messageConsumerList.forEach(one -> {
            if (Objects.nonNull(messageConsumerMap.put(one.topic(), one))) {
                log.error("禁止重复监听相同的topic");
                throw new RuntimeException("禁止重复监听相同的topic");
            }
        });
    }
}
