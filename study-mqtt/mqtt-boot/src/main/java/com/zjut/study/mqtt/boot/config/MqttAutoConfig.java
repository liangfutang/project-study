package com.zjut.study.mqtt.boot.config;

import com.zjut.study.mqtt.boot.properties.MqttProperties;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import javax.annotation.Resource;

/**
 * @author jack
 */
@Configuration
@IntegrationComponentScan
public class MqttAutoConfig {

    @Resource
    private MqttProperties mqttProperties;

    /**
     * @return 客户端工厂
     */
    @Bean
    public MqttPahoClientFactory mqttPahoClientFactory(){
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(mqttProperties.getHostUrl().split(","));
        options.setUserName(mqttProperties.getUsername());
        options.setPassword(mqttProperties.getPassword().toCharArray());
        factory.setConnectionOptions(options);
        return factory;
    }

    /**
     * @return 出站消息管道
     */
    @Bean
    public MessageChannel mqttOutboundChannel(){
        return new DirectChannel();
    }

    /**
     * @return 入站消息管道
     */
    @Bean
    public MessageChannel mqttInboundChannel(){
        return new DirectChannel();
    }

    /**
     * @param factory 客户端工厂
     * @return Mqtt 管道适配器
     */
    @Bean
    public MqttPahoMessageDrivenChannelAdapter adapter(MqttPahoClientFactory factory){
        return new MqttPahoMessageDrivenChannelAdapter(mqttProperties.getInClientId(),factory,mqttProperties.getDefaultTopic().split(","));
    }

    /**
     * 消息生产者
     * @param adapter 适配器
     * @return 消息生产者
     */
    @Bean
    public MessageProducer mqttInbound(MqttPahoMessageDrivenChannelAdapter adapter){
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        // 入站投递的通道
        adapter.setOutputChannel(mqttInboundChannel());
        adapter.setQos(1);
        return adapter;
    }

    /**
     * 使用ServiceActivator 指定接收消息的管道为 mqttInboundChannel，投递到mqttInboundChannel管道中的消息会被该方法接收并执行
     * @return 订阅消息处理器
     */
    @Bean
    @ServiceActivator(inputChannel = "mqttInboundChannel")
    public MessageHandler handleMessage() {
        // 这个 mqttMessageHandle 其实就是一个 MessageHandler 的实现类(这个类我放下面)
//        return null;
        // 你也可以这样写
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                // do something
                System.out.println(message);
            }
        };
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound(MqttPahoClientFactory factory){
        MqttPahoMessageHandler handler = new MqttPahoMessageHandler(mqttProperties.getOutClientId(),factory);
        handler.setAsync(true);
        handler.setConverter(new DefaultPahoMessageConverter());
        handler.setDefaultTopic(mqttProperties.getDefaultTopic().split(",")[0]);
        return handler;
    }

}
