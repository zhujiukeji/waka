package com.zhujiu.waka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;

@SpringBootApplication
@EnableDubboConfiguration
@ComponentScan("com.zhujiu.waka")
public class SocketServer {

	public static void main(String[] args) {
		SpringApplication.run(SocketServer.class, args);

	}

}
