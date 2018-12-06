package com.zhujiu.waka;

import java.util.concurrent.CountDownLatch;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;


@SpringBootApplication
@EnableDubboConfiguration
@ComponentScan("com.zhujiu.waka")
public class UserServer implements CommandLineRunner,DisposableBean{
	private static CountDownLatch latch = new CountDownLatch(1);
	private static Logger log=Logger.getLogger(UserServer.class);
	public static void main(String[] args) throws InterruptedException {
		SpringApplication app = new SpringApplication(UserServer.class);
		app.setWebApplicationType(WebApplicationType.NONE);
		app.run(args);
		latch.await();
	}
	@Override
	public void destroy() throws Exception {
		latch.countDown();
		
	}
	@Override
	public void run(String... args) throws Exception {
		log.info("=======User服务器启动成功！");
		
	}
	
	@Bean
	public JdbcTemplate createTemplate(DataSource datasource) {
		return new JdbcTemplate(datasource);
	}
}
