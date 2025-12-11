package com.wangxinye.copal.service.impl;

import com.wangxinye.copal.entity.SysPermission;
import com.wangxinye.copal.entity.SysRole;
import com.wangxinye.copal.entity.SysUser;
import com.wangxinye.copal.exception.UserNotFoundException;
import com.wangxinye.copal.mapper.SysPermissionMapper;
import com.wangxinye.copal.mapper.SysRoleMapper;
import com.wangxinye.copal.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysPermissionMapper sysPermissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserMapper.selectByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("用户名不存在或账户已禁用");
        }

        List<SysRole> roles = sysRoleMapper.selectByUserId(user.getUserId());
        user.setRoles(roles);

        if (!roles.isEmpty()) {
            List<Long> roleIds = roles.stream().map(SysRole::getRoleId).collect(Collectors.toList());
            List<SysPermission> permissions = sysPermissionMapper.selectByRoleIds(roleIds);
            user.setPermissions(permissions);
        }

        return user;
    }
}