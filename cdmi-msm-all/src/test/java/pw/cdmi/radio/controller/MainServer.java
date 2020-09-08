package pw.cdmi.radio.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@EnableAuthorizationServer
public class MainServer {
    public static void main(String[] args) throws Exception {
//        Log4jUtils.init();             //# 日志初始化
//        BeanUtils.init();              //# Spring bean初始化
        SpringApplication.run(MainServer.class, args);
    }
}