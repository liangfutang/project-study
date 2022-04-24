package com.zjut.study.dubbo.consumer.config;

import com.zjut.study.dubbo.consumer.properties.DubboProperties;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ConsumerConfig;
import org.apache.dubbo.config.MonitorConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class DubboConfiguration {

    private final DubboProperties dubboProperties = new DubboProperties();

    @Value("${nacos.discovery.namespace}")
    private String nacosDiscoveryNamespace;

    @Bean
    public ApplicationConfig applicationConfig() {
        // 当前应用配置
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(dubboProperties.getApplicationName());
        applicationConfig.setEnvironment(dubboProperties.getApplicationEnvironment());
        // QOS是dubbo的在线运维命令,默认端口2222，本地同时启动两个dubbo服务，端口冲突了，这里暂时先关闭
        applicationConfig.setQosEnable(false);
        applicationConfig.setQosAcceptForeignIp(false);
        return applicationConfig;
    }

    /**
     * 注入dubbo注册中心配置,基于nacos
     *
     * @return
     */
    @Bean
    public RegistryConfig registryConfig() {
        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setProtocol(dubboProperties.getProtocol());
        registry.setAddress(dubboProperties.getRegistryAddress());
        registry.setGroup("DUBBO_GROUP");

        //指定nacos环境变量
        registry.setParameters(new HashMap<String, String>(2) {{
            // namespace从服务启动参数注入了，所以从启动参数中直接获取
            put("namespace", nacosDiscoveryNamespace);
        }});

        return registry;
    }

    @Bean
    public MonitorConfig monitorConfig() {
        // 连接注册中心配置
        MonitorConfig registry = new MonitorConfig();
        registry.setProtocol(dubboProperties.getMonitorProtocol());
        registry.setAddress(dubboProperties.getRegistryAddress());
        return registry;
    }

    /**
     * 默认基于dubbo协议提供服务
     *
     * @return
     */
//    @Bean(name = "defaultConsumer")
//    public ProviderConfig providerConfig() {
//
//        ProviderConfig providerConfig = new ProviderConfig();
//        providerConfig.setGroup("dubbo");
////        if (DevParamUtil.isLocalTest()) {
////            providerConfig.setGroup(DevParamUtil.getDubboGroupName());
////        }
//
//        providerConfig.setFilter("dubboTraceIdFilter,dubboProviderFilter");
//        return providerConfig;
//    }

    @Bean
    public ProtocolConfig protocolConfig() {
        // 服务提供者协议配置
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName(dubboProperties.getProtocolName());
        protocolConfig.setPort(dubboProperties.getProtocolPort());
        return protocolConfig;
    }

    /**
     * dubbo消费
     */
    @Bean
    public ConsumerConfig consumerConfig() {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setCheck(false);
        consumerConfig.setGroup("dubbo");
//        if (DevParamUtil.isLocalTest()) {
//            consumerConfig.setGroup(DevParamUtil.getDubboGroupName());
//        }

        consumerConfig.setFilter("dubboTraceIdFilter,dubboConsumerFilter");
        return consumerConfig;
    }
}
