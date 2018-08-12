package com.notingtodo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;



/**
 * 基础授权服务
 */
@SpringBootApplication
@EnableEurekaClient
public class BasicAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicAuthApplication.class, args);
	}

//	@Bean(name = "auditorAware")
//	public AuditorAware<String> auditorAware(){
//		return ()-> SecurityUtils.getCurrentUserUsername();
//	}

}
