package com.notingtodo.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by qilin.liu on 2018/6/20.
 */
public final class SecurityUtils {

    /**
     * 获取用户名
     * @return
     */
    public static String getCurrentUserUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = null;
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            currentUserName = authentication.getName();
        }
        return currentUserName;
    }
}
