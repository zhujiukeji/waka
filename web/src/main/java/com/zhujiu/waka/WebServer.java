package com.zhujiu.waka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;

@EnableAutoConfiguration
@SpringBootApplication
@EnableDiscoveryClient
@EnableDubboConfiguration
@ComponentScan("com.zhujiu.waka")
public class WebServer {

	public static void main(String[] args) {
		SpringApplication.run(WebServer.class, args);
	}
}
