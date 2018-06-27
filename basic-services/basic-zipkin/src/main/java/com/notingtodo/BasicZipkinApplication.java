package com.notingtodo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import zipkin.server.EnableZipkinServer;


@SpringBootApplication
@EnableZipkinServer
@EnableEurekaClient
public class BasicZipkinApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicZipkinApplication.class, args);
	}
}
