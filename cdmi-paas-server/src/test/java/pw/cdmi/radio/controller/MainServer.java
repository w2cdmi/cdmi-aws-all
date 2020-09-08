package pw.cdmi.radio.controller;

import org.apache.servicecomb.foundation.common.utils.Log4jUtils;
import org.apache.servicecomb.springboot.starter.provider.EnableServiceComb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@EnableServiceComb //新增注解
@EnableAuthorizationServer
public class MainServer {
    public static void main(String[] args) throws Exception {
        Log4jUtils.init();             //# 日志初始化
//        BeanUtils.init();              //# Spring bean初始化
        SpringApplication.run(MainServer.class, args);
    }
}