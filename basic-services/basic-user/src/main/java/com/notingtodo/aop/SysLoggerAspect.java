package com.notingtodo.aop;

import com.alibaba.fastjson.JSON;
import com.notingtodo.entity.SysLog;
import com.notingtodo.annotation.SysLogger;
import com.notingtodo.service.LoggerService;
import com.notingtodo.util.HttpUtils;
import com.notingtodo.util.UserUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * 切面--主要是切面进行日志输出
 * Created by qilin.liu on 2018/6/30.
 */
@Aspect
@Component
public class SysLoggerAspect {

    @Autowired
    private LoggerService loggerService;

    /**
     * 切入点,如果调用该注解，将会调用到此方法
     */
    @Pointcut("@annotation(com.notingtodo.annotation.SysLogger)")
    public void loggerPointCut(){}

    /**
     * 在调用 loggerPointCut() 方法之前会进行下面方法的调用 Before() 注解
     */
    @Before("loggerPointCut()")
    public void saveSysLog(JoinPoint joinPoint){
            //获得方法签名
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取方法
            Method method = signature.getMethod();
            //实例化日志实体
            SysLog sysLog =new SysLog();
            //获取该注解(就是系统日志注解)
            SysLogger sysLogger = method.getAnnotation(SysLogger.class);
            if(sysLogger != null){
                //注解上的描述 添加到系统日志的用户操作属性中
                sysLog.setOperation(sysLogger.value());
            }
            //请求的方法名
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = signature.getName();
            sysLog.setMethod(className+"."+methodName+"()");
            //请求参数
            Object[] args =joinPoint.getArgs();
            String params = "";
            for(Object o : args){
                params += JSON.toJSONString(o); //如果参数不为空则将参数对象转化成字符串
            }
            if(!StringUtils.isEmpty(params)){
                sysLog.setParams(params); //如果参数字符串不为空，设置到日志对象中
            }
            //设置IP地址
            sysLog.setIp(HttpUtils.getIpAddress());
            //用户名
            String username = UserUtils.getCurrentPrinciple();
            if(!StringUtils.isEmpty(username)){
                sysLog.setUsername(username);
            }
            //创建日期
            sysLog.setCreateDate(new Date());
            //保存系统日志
        loggerService.log(sysLog);
    }
}