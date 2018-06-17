package com.notingtodo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by qilin.liu on 2018/6/17.
 */
@ComponentScan
@Configuration
@EnableJpaRepositories
public class ApplicationConfig {
}
