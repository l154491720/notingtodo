package com.notingtodo.web;

import com.notingtodo.annotation.SysLogger;
import com.notingtodo.dto.RespDTO;
import com.notingtodo.entity.User;
import com.notingtodo.exception.CommonException;
import com.notingtodo.exception.ErrorCode;
import com.notingtodo.service.UserService;
import com.notingtodo.util.BPwdEncoderUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * token前面需要家 Bearer xxx
 * Created by qilin.liu on 2018/6/30.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "注册",notes = "username和password为必选项")
    @PostMapping("/registry")
    @SysLogger("registry")
    public User createUser(@RequestBody User user){
        //判读当前用户传入参数是否合法
        if(StringUtils.isEmpty(user.getUsername())){
            throw new CommonException(ErrorCode.REQUEST_PARAMS_ERROR);
        }
        if(StringUtils.isEmpty(user.getPassword())){
            throw new CommonException(ErrorCode.REQUEST_PARAMS_ERROR);
        }
        //判读改用户在数据库是否已经存在
        if(userService.getUserInfo(user.getUsername())!= null){
            throw new CommonException(ErrorCode.USER_ALREADY_EXIST);
        }
        String entryPassword = BPwdEncoderUtils.BCryptPassword(user.getPassword());
        user.setPassword(entryPassword);
        return userService.createUser(user);
    }

    @ApiOperation(value = "登录",notes = "username和password为必选项")
    @PostMapping("/login")
    @SysLogger("login")
    public RespDTO login(@RequestParam String username, @RequestParam String password){
        if(StringUtils.isEmpty(username)){
            throw new CommonException(ErrorCode.REQUEST_PARAMS_ERROR);
        }
        if(StringUtils.isEmpty(password)){
            throw new CommonException(ErrorCode.REQUEST_PARAMS_ERROR);
        }
        return userService.login(username,password);
    }

    @ApiOperation(value = "根据用户名获取用户", notes = "根据用户名获取用户")
    @PostMapping("/{username}")
    @SysLogger("getUserInfo")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public RespDTO getUserInfo(@PathVariable("username")String username){
        if(StringUtils.isEmpty(username)){
            throw new CommonException(ErrorCode.REQUEST_PARAMS_ERROR);
        }
        User user =userService.getUserInfo(username);
        return RespDTO.onSuc(user);
    }
}