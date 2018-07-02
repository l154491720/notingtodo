package com.notingtodo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


/**
 * 智能路由网关组件 Zuul
 *  Zuul 作为微服务系统的网关组件，用于构建边界服务(Edge Service),致力于动态路由、过滤、监控、弹性伸缩和安全。
 *
 *	Zuul 作为网关组件，在微服务架构中有着非常重要的作用，主要体现在一下6个方面。
 *	1.Zuul、Ribbon 以及 Eureka 相结合, 可以实现实现负载均衡的功能，Zuul 能够将请求流量按照某种策略发送到集群状态
 *		的多个实例。
 *	2.网关将所有服务的 API 接口统一聚合，并统一对外暴露。外接系统调用 API 接口时， 都是由网关对外暴露的 API 接口
 *		外接系统不需要知道微服务系统中各服务相互调用的复杂性。微服务系统也保护了其内部服务单元的 API 接口，防止其
 *		被外界直接调用，导致服务的敏感信息对外暴露。
 *	3.网关可以做用户身份认证和权限认证，防止非法请求操作 API 接口，对服务器起到保护作用。
 *	4.网关可以实现监控功能，实时日志输出，对请求进行记录
 *	5.网关可以用来实现流量监控，在高流量的情况下，对服务进行降级。
 *  6.API 接口从内部服务分离出来，方便做测试
 *
 *
 *  Zuul 是通过 Servlet 来实现的, Zuul 通过自定义的 ZuulServlet 来对请求进行控制。其核心是一系列过滤器
 *  	1.PRE过滤器: 请求路由到具体服务之前执行，可以做安全验证。
 *  	2.ROUTING过滤器: 用于将请求路由导具体的微服务实例
 *  	3.POST过滤器: 在请求已被路由到微服务后执行，一般情况下做收集统计信息、指标，以及相应输出到客户端
 *  	4.ERROR过滤器: 在其他过滤器发生错误时执行.
 *  Zuul采取了动态读取、编译和运行这些过滤器。过滤器之间不能直接相互通信，而是通过 RequestContext 对象来
 *  共享数据，每个请求都会创建一个 RequestContext 对象。 Zuul 过滤器具有以下关键性特征。
 *  	1.Type(类型): Zuul 过滤器的类型决定了过滤器在请求的哪个阶段起作用，例如 Pre 、 Post 阶段
 *  	2.Execution Oeder(执行顺序): 规定了过滤器的执行顺序，Order 的值越小，越先执行。
 *  	3.Criteria(标准): Filter 执行所需的条件
 *  	4.Action(行动): 如果符合条件，则执行Action(即逻辑代码)
 *
 *  当一个客户端 Request 请求进入 Zuul 网关服务时，网关先进入 "pre filter", 进行一系列验证、操作或者判断。
 *  然后交给"routing filter" 进行路由转发， 转发到具体的服务实例进行逻辑处理、返回数据。单具体的服务处理
 *  完后，最后由"post filter" 进行处理，该类型的处理器处理完之后，将 Response 信息反馈给客户端。
 *
 */
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class BasicGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicGatewayApplication.class, args);
	}
}
