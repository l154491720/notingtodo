package com.notingtodo.repository.support;

import com.notingtodo.domain.SysUser;

import java.util.Optional;

/**
 * Created by qilin.liu on 2018/6/20.
 */
public interface SysUserRepository extends BaseRepository<SysUser,Long> {
    Optional<SysUser> findOneWithRolesByUsername(String username);
}
