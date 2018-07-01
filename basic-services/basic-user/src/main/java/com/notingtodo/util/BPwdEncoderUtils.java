package com.notingtodo.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码解密工具类
 * Created by qilin.liu on 2018/6/30.
 */
public class BPwdEncoderUtils {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * 用BCryptPasswordEncoder 解密密码
     */
    public static String BCryptPassword(String password){
        return encoder.encode(password);
    }

    /**
     * 匹配原始密码和加密后密码
     */
    public static boolean matches(CharSequence rawPassword, String encodePassword){
        return encoder.matches(rawPassword, encodePassword);
    }
}