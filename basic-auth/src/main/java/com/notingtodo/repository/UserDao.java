package com.notingtodo.repository;

import com.notingtodo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by qilin.liu on 2018/6/30.
 */
public interface UserDao  extends JpaRepository<User,Long>{
    User findByUsername(String username);
}