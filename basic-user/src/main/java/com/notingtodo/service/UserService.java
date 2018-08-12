package com.notingtodo.service;

import com.notingtodo.client.AuthServiceClient;
import com.notingtodo.dao.UserDao;
import com.notingtodo.dto.LoginDto;
import com.notingtodo.dto.RespDTO;
import com.notingtodo.entity.JWT;
import com.notingtodo.entity.User;
import com.notingtodo.exception.CommonException;
import com.notingtodo.exception.ErrorCode;
import com.notingtodo.util.BPwdEncoderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户接口
 * Created by qilin.liu on 2018/6/30.
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    AuthServiceClient authServiceClient;

    /**
     * 创建用户的方法
     */
    public User createUser(User user){
        return userDao.save(user);
    }

    /**
     * 获取用户信息方法
     */
    public User getUserInfo(String username){
        return userDao.findByUsername(username);
    }

    /**
     * 用户登录方法
     */
    public RespDTO login(String username, String password){
        User user = userDao.findByUsername(username);
        if(null == user){ //如果找不到用户，抛出公共异常对象
            throw new CommonException(ErrorCode.USER_NOT_FOUND);
        }
        if(!BPwdEncoderUtils.matches(password,user.getPassword())){
            throw new CommonException(ErrorCode.USER_PASSWORD_ERROR);
        }

        JWT jwt = authServiceClient.getToken("Basic dWFhLXNlcnZpY2U6MTIzNDU21","password", username,password);
        //获得用户菜单
        if(null == jwt){
            throw new CommonException(ErrorCode.GET_TOKEN_FAIL);
        }
        LoginDto loginDto =new LoginDto();
        loginDto.setUser(user);
        loginDto.setToken(jwt.getAccess_token());
        return RespDTO.onSuc(loginDto);
    }
}