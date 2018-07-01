package com.notingtodo.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 * Created by qilin.liu on 2018/6/30.
 */
@Target(ElementType.METHOD) //此注解为方法注解
@Retention(RetentionPolicy.RUNTIME) //运行时编译此注解
@Documented
public @interface SysLogger {
    String value() default ""; //给一个默认参数为空字符串
}