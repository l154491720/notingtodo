package com.notingtodo.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by qilin.liu on 2018/6/30.
 */
public class HttpUtils {


    /**
     * 尝试获取当前请求的HttpServletRequest实例
     * @return
     */
    public static HttpServletRequest getHttpServletRequest(){
        try{
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 获取请求头
     */
    public static Map<String,String> getHeader(HttpServletRequest request){
        Map<String,String> map = new LinkedHashMap<>();
        //获取请求头
        Enumeration<String> enumeration = request.getHeaderNames();
        //遍历请求头，将请求头中的键与值插入map
        while (enumeration.hasMoreElements()){
            String key = enumeration.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    /**
     * 获取真实IP地址
     */
    public static String getIpAddress(HttpServletRequest request){
        //获取请求主机IP地址，如果通过代理进来，则透过防火墙获取真实IP地址
        String ip = request.getHeader("X-Forwarded-For");

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        }else if(ip.length() > 15){
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++){
                String strIp = (String) ips[index];
                if(!("unknown").equalsIgnoreCase(strIp)){
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }

    /**
     * 获取请求客户端的真实IP地址
     */
    public static String getIpAddress(){
        //获取请求主机IP地址，如果通过代理进来，则透过防火墙获取真实地址
        return getIpAddress(getHttpServletRequest());
    }
}