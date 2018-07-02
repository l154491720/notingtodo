package com.notingtodo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import zipkin.server.EnableZipkinServer;


/**
 *	链路追踪 Spring Cloud Sleuth
 * 	Spring Cloud Sleuth 是 Spring Cloud 的一个组件，它的主要功能时在分布式系统中提供服务追踪链路解决方案
 * 	1.Span: 基本工作单元，发送一个远程调度任务就会产生一个 Span, Span 是一个用64位 ID 唯标识的，Span还包含
 * 		其他信息，例如摘要、时间戳、Span的ID以及进程ID。
 * 	2.Trace: 由一系列 Span 组成的，成树状结构，请求一个微服务系统的 API 接口，这个 API 接口需要调用多个微服务
 * 		微服务单元，调用每个微服务单元都会产生一个新的 Span , 所有由这个请求产生的 Span 会产生一个 Trace。
 * 	3.Annotation: 用于记录一件事，一些核心注解用于定义一个请求的开始和结束。这些注解如下
 *
 * 	·cs-Client Sent: 客户端发送一个请求，这个注解描述了 Span 的开始。
 * 	·sr-Server Received: 服务端获取请求并准备开始处理它，如果将其 sr 减去 cs 时间戳，便可得到网络传输时间
 * 	·ss-Server Sent: 服务端发送响应，该注解表明请求处理完成。ss-sr，便可以得到服务器请求的时间
 * 	·cr-Client Received: 客户端接收响应，此时 Span 结束，cr-cs, 便可以得到整个请求消耗的时间。
 */
@SpringBootApplication
@EnableZipkinServer
@EnableEurekaClient
public class BasicZipkinApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicZipkinApplication.class, args);
	}
}
