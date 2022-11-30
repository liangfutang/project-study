package com.zjut.study.mqtt.boot.entity;

import lombok.Data;

/**
 * 消息的基本信息
 * @author jack
 */
@Data
public class BaseMessage {

    private String topic;

    private int qos;
}
