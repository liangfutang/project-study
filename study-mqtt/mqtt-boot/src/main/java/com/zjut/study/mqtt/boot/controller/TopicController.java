package com.zjut.study.mqtt.boot.controller;

import com.zjut.study.common.convention.result.Result;
import com.zjut.study.common.convention.result.Results;
import com.zjut.study.mqtt.boot.entity.Topic;
import org.apache.commons.lang3.StringUtils;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * topic相关
 * @author jack
 */
@RestController()
@RequestMapping("topic")
public class TopicController {

    @Resource
    private MqttPahoMessageDrivenChannelAdapter adapter;

    @PostMapping()
    public Result<String> addTopic(@RequestBody Topic topic) {
        String[] topics = adapter.getTopic();
        if (StringUtils.isNotBlank(topic.getTopic())
                && !Arrays.asList(topics).contains(topic.getTopic())) {
            adapter.addTopic(topic.getTopic(), topic.getQos());
        }
        return Results.success("success");
    }
}
