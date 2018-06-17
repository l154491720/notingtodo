package com.notingtodo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableConfigServer //1 使用此注解开启配置服务器的支持
@EnableEurekaClient //2 使用此注解开启Eureka Server的客户端的支持
public class BasicConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicConfigApplication.class, args);
	}
}
