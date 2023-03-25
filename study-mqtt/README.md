
# 一. 原理
## 1.1 报文
[报文详细说明文档](https://mcxiaoke.gitbooks.io/mqtt-cn/content/mqtt/01-Introduction.html)     

[报文介绍比较好的视频](https://www.bilibili.com/video/BV1oJ411176Q/?p=1&vd_source=70eb1facbc8a6b0cc0b7b0efb3c281bc)
![报文组成](./pic/报文组成.png)   
![connect连接报文示意](./pic/connect报文内容示意.png)  
![固定报头](./pic/固定报头.png)    
![可变报头-协议名](./pic/协议名.png)   
![可变报头-协议级别](./pic/协议级别.png)   
![可变报头-连接标志](./pic/连接标志.png)   
![可变报头-保持连接](./pic/保持连接.png)    

![有效载荷](./pic/有效载荷.png)   

### 1.1.1 connect
阿里云连接地址：iot-06z00fgrxhwetz7.mqtt.iothub.aliyuncs.com:1883
[connect连接博客](https://blog.csdn.net/daniaoxp/article/details/103039296)
![连接返回响应码](./pic/连接返回响应码.png)

三要素
```json
{
  "ProductKey": "hm5wlky7r5G",
  "DeviceName": "ld0001",
  "DeviceSecret": "9bd11c83bcad652162be937c2c81b0f9"
}
```
请求报文拼接
```
固定报头的拼装：
	10 ??  (matt报文类型+保留位，固定的，问好是剩余长度，是可变报文加有效载荷的总长度)
	
可变报头的拼接：
	00 04 4D 51 54 54 04 C2 00 64  (协议名、用户密码、消息级别、保活时间等参数组装后的hex，共十个字节，基本固定)

有效载荷的拼装：
客户端id: ld0001|securemode=3,signmethod=hmacsha1|  
	转成十六进制：6C 64 30 30 30 31 7C 73 65 63 75 72 65 6D 6F 64 65 3D 33 2C 73 69 67 6E 6D 65 74 68 6F 64 3D 68 6D 61 63 73 68 61 31 7C  
	长度:40 长度的hex:0x28
	最终报文hex: 00 28 6C 64 30 30 30 31 7C 73 65 63 75 72 65 6D 6F 64 65 3D 33 2C 73 69 67 6E 6D 65 74 68 6F 64 3D 68 6D 61 63 73 68 61 31 7C  
用户名： ld0001&hm5wlky7r5G                        
	转成十六进制： 6C 64 30 30 30 31 26 68 6D 35 77 6C 6B 79 37 72 35 47             
	长度:18    长度的hex: 0x12
	最终报文hex: 00 12 6C 64 30 30 30 31 26 68 6D 35 77 6C 6B 79 37 72 35 47 
密码： clientIdld0001deviceNameld0001productKeyhm5wlky7r5G  sha1加密：7df55dfa3629a8c4a1bb8fe22a353e19c8e88da2
	转成十六进制：37 64 66 35 35 64 66 61 33 36 32 39 61 38 63 34 61 31 62 62 38 66 65 32 32 61 33 35 33 65 31 39 63 38 65 38 38 64 61 32     
	长度:40  长度的hex:0x28
	最终报文hex: 00 28 37 64 66 35 35 64 66 61 33 36 32 39 61 38 63 34 61 31 62 62 38 66 65 32 32 61 33 35 33 65 31 39 63 38 65 38 38 64 61 32  

最终拼接：
10 72 00 04 4D 51 54 54 04 C2 00 64 00 28 6C 64 30 30 30 31 7C 73 65 63 75 72 65 6D 6F 64 65 3D 33 2C 73 69 67 6E 6D 65 74 68 6F 64 3D 68 6D 61 63 73 68 61 31 7C 00 12 6C 64 30 30 30 31 26 68 6D 35 77 6C 6B 79 37 72 35 47 00 28 37 64 66 35 35 64 66 61 33 36 32 39 61 38 63 34 61 31 62 62 38 66 65 32 32 61 33 35 33 65 31 39 63 38 65 38 38 64 61 32
```





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



