package com.notingtodo.security;

import com.notingtodo.domain.SysUser;
import com.notingtodo.repository.support.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

/**
 * Created by qilin.liu on 2018/6/20.
 */
public class DomainUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String locaseUsername = username.toLowerCase();
        Optional<SysUser> realUser = sysUserRepository.findOneWithRolesByUsername(locaseUsername);
        return realUser.map(user->{
            Set<GrantedAuthority> grantedAuthorities =user.getAuthorities();
            return new User(user.getUsername(), user.getPassword(),grantedAuthorities);
        }).orElseThrow(()-> new UsernameNotFoundException("用户"+locaseUsername+"不存在"));
    }
}
