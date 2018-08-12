package com.notingtodo.service;

import com.alibaba.fastjson.JSON;
import com.notingtodo.config.RabbitConfig;
import com.notingtodo.entity.SysLog;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Created by qilin.liu on 2018/6/30.
 */
@Service
public class LoggerService {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void log(SysLog sysLog){
        rabbitTemplate.convertAndSend(RabbitConfig.queueName, JSON.toJSON(sysLog));
    }
}