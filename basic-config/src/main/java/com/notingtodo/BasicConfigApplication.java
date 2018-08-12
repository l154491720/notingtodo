package com.notingtodo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 分布式配置中心 Spring Cloud Config
 * Config Server 可以从本地仓库读取配置文件，也可以从远处 Git 仓库读取。本地仓库指的是将所有的配置
 * 文件统一写在 Config Server 工程目录下。 Config Server 暴露 Http API 接口, Config Client 通过调用
 * Config Server 的 Http API 接口来读取配置文件
 *
 * 注：
 * 	1.bootstrap 相对于 application 具有优先的执行顺序
 * 	2.Config Server 读取配置文件。如果没有读取成功，则执行快速失败(fail-fast)
 * 	3.bootstrap.yml 配置文件中的变量{spring.application.name}和变量{spring.profiles.active}，
 * 		两者以"-"相连，构成向Config Server 读取的配置文件名。
 *
 * Spring Cloud Config 支持从远程 Git 仓库读取配置文件，即 Config Server 可以不从本地仓库读取，而是从远程 Git
 * 仓库读取。这样做的好处就是将配置文件统一管理，并且可通过 Spring Cloud Bus 在不人工启动程序的情况下对 Config
 * Client 的配置进行刷新，
 */
@SpringBootApplication
@EnableConfigServer //1 使用此注解开启配置服务器的支持
@EnableEurekaClient //2 使用此注解开启Eureka Server的客户端的支持
public class BasicConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicConfigApplication.class, args);
	}
}
