//package com.zjut.study.dubbo.provider.config;
//
//import com.alibaba.nacos.api.annotation.NacosInjected;
//import com.alibaba.nacos.api.exception.NacosException;
//import com.alibaba.nacos.api.naming.NamingService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.PostConstruct;
//import java.net.Inet4Address;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.util.Optional;
//
///**
// * nacos注册中心
// * @author liangfu.tang
// */
//@Configuration
//@Slf4j
//public class NacosRegisterConfiguration {
//
//
//    @NacosInjected
//    private NamingService namingService;
//
//    @PostConstruct
//    public void registerInstance() throws NacosException {
//        InetAddress localHost = null;
//        try {
//            localHost = Inet4Address.getLocalHost();
//        } catch (UnknownHostException e) {
//            log.error(e.getMessage(),e);
//        }
//        String ip = Optional.ofNullable(localHost).map(InetAddress::getHostAddress).orElse("0");
//        namingService.registerInstance("dubbo-provider", "APPLICATION_GROUP", ip, 8088, "default");
//    }
//
//}
