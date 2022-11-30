
# 一. mqtt说明
## 1.1 配置说明

**访问emqt的web管理页面**
http://127.0.0.1:18083
#账号： admin
#密码: public

**端口**
1883：MQTT 协议端口
8084：MQTT/SSL 端口
8083：MQTT/WebSocket 端口
8080：HTTP API 端口
18083：Dashboard 管理控制台端口

# 二. 内部模块划分
## 2.1 mqtt-boot
+ 消费订阅消息
只要实现了`com.zjut.study.mqtt.boot.config.handle.MessageConsumer`接口并注入到spring容器即可，如`com.zjut.study.mqtt.boot.consumer.TopicMqttBootUpConsumer`