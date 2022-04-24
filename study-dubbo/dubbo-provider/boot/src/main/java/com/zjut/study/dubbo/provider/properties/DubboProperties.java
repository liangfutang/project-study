package com.zjut.study.dubbo.provider.properties;

import lombok.Data;

/**
 * dubbo配置参数
 */
@Data
public class DubboProperties {

    private String applicationName = "dubbo-provider";

    private String applicationEnvironment = "test";

    private String protocol = "nacos";
    /**
     * dubbo协议名称
     */
    private String protocolName = "dubbo";
    /**
     * dubbo服务端口号
     */
    private Integer protocolPort = 20890;

    private String registryAddress = "127.0.0.1:8848";

    private String monitorProtocol = "registry";

    private String registryGroup = "dubbo";

}
