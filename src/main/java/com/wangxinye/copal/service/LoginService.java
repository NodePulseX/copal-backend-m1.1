package com.wangxinye.copal.service;

import com.wangxinye.copal.dto.LoginRequestDTO;
import com.wangxinye.copal.dto.LoginResponseDTO;
import com.wangxinye.copal.entity.SysPermission;
import com.wangxinye.copal.entity.SysRole;
import com.wangxinye.copal.entity.SysUser;
import com.wangxinye.copal.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        SysUser user = (SysUser) authentication.getPrincipal();

        String token = jwtUtil.generateToken(user.getUsername());

        List<String> roles = user.getRoles().stream()
                .map(SysRole::getRoleCode)
                .collect(Collectors.toList());

        List<String> permissions = user.getPermissions().stream()
                .map(SysPermission::getPermissionCode)
                .collect(Collectors.toList());

        return LoginResponseDTO.builder()
                .token(token)
                .userInfo(LoginResponseDTO.UserInfoDTO.builder()
                        .userId(user.getUserId())
                        .username(user.getUsername())
                        .nickName(user.getNickName())
                        .email(user.getEmail())
                        .phone(user.getPhone())
                        .avatar(user.getAvatar())
                        .deptId(user.getDeptId())
                        .loginCount(user.getLoginCount())
                        .build())
                .roles(roles)
                .permissions(permissions)
                .build();
    }

    public void logout() {
        SecurityContextHolder.clearContext();
    }
}