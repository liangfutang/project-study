package com.zjut.study.mqtt.boot.entity;

import lombok.Data;

/**
 * @author jack
 */
@Data
public class Ota extends BaseMessage {
    private String url;
    private Integer size;
}
