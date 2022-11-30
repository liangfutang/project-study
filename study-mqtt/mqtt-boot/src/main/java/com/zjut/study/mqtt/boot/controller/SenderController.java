package com.zjut.study.mqtt.boot.controller;

import com.alibaba.fastjson.JSONObject;
import com.zjut.study.common.convention.result.Result;
import com.zjut.study.common.convention.result.Results;
import com.zjut.study.mqtt.boot.config.handle.MqttGateway;
import com.zjut.study.mqtt.boot.entity.Ota;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 发送消息
 * @author jack
 */
@RestController
@RequestMapping("/sender")
public class SenderController {

    @Resource
    private MqttGateway mqttGateway;

    /**
     * 简单的发送一条测试消息
     * @param ota
     * @return
     */
    @PostMapping()
    public Result<?> send(@RequestBody Ota ota) {
        JSONObject data = new JSONObject();
        data.put("url", ota.getUrl());
        data.put("size", ota.getSize());
        mqttGateway.sendToMqtt(ota.getTopic(), data.toJSONString());
        return Results.success("success");
    }

}
