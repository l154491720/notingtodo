package com.notingtodo.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

/**
 * 用户工具类
 * Created by qilin.liu on 2018/6/30.
 */
public class UserUtils {

    private static final String AUTHORIZATION = "authorization";

    /**
     * 获取当前请求的token
     */
    public static String getCurrentToken(){
        return HttpUtils.getHeader(HttpUtils.getHttpServletRequest()).get(AUTHORIZATION);
    }

    /**
     * 获取当前请求的用户ID
     */
    public static String getCurrentPrinciple(){
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 判断当前token用户是否为接口所需的参数username
     */
    public static boolean isMyself(String username){
        return username.equals(getCurrentPrinciple());
    }

    /**
     * 获取当前请求Authentication
     */
    public static Authentication getCurrentAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取当前请求的权限信息
     */
    public static List<SimpleGrantedAuthority> getCurrentAuthorities(){
        return (List<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }


    public static boolean hasRole(String role){
        if(!role.startsWith("ROLE ")){
            role = "ROLE "+role;
        }
        boolean hasRole = false;
        List<SimpleGrantedAuthority> list = getCurrentAuthorities();
        for (SimpleGrantedAuthority authority : list) {
            if(role.equals(authority.getAuthority())){
                hasRole = true;
                break;
            }
        }
        return hasRole;
    }
}