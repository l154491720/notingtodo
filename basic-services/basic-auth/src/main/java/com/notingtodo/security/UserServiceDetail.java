package com.notingtodo.security;


import com.notingtodo.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *  UserServiceDetail 需要实现 UserDetailsService ，改接口是根据用户获取该用户的所有信息，包括用户信息和权限点
 * Created by qilin.liu on 2018/6/30.
 */
@Service
public class UserServiceDetail implements UserDetailsService{

    @Autowired
    private UserDao userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s);
    }
}