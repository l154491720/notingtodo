package com.notingtodo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息队列配置类
 * Created by qilin.liu on 2018/6/30.
 */
@Configuration
public class RabbitConfig {

    /**
     * 配置消息队列名称
     */
    public final static String queueName = "spring-boot";

    /**
     * 实例化队列，并注入IOC容器
     */
    @Bean
    Queue queue(){
        return new Queue(queueName,false);
    }

    /**
     * 实例化TopicExchange，该exchange关系的主题为 "spring-boot-exchange"
     */
    @Bean
    TopicExchange exchange(){
        return new TopicExchange("spring-boot-exchange");
    }

    /**
     * 绑定队列和Exchange
     */
    @Bean
    Binding binding(Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(queueName);
    }
}