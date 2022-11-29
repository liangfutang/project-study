package com.zjut.study.mqtt.boot.entity;

import lombok.Data;

/**
 * @author jack
 */
@Data
public class Topic {

    private String topic;

    private int qos;
}
