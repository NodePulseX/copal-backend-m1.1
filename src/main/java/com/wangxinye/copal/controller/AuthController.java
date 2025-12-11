package com.wangxinye.copal.controller;

import com.wangxinye.copal.dto.LoginRequestDTO;
import com.wangxinye.copal.dto.LoginResponseDTO;
import com.wangxinye.copal.dto.R;
import com.wangxinye.copal.entity.SysUser;
import com.wangxinye.copal.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;

    @PostMapping("/login")
    public R<LoginResponseDTO> login(@Validated @RequestBody LoginRequestDTO loginRequest) {
        LoginResponseDTO response = loginService.login(loginRequest);
        return R.success(response);
    }

    // 修正：用新增的success(String message)方法，匹配R<Void>返回类型
    @PostMapping("/logout")
    public R<Void> logout() {
        loginService.logout();
        return R.success("退出登录成功");
    }

    @GetMapping("/current-user")
    public R<SysUser> getCurrentUser() {
        SysUser user = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword(null);
        user.setAccountNonExpired(null);
        user.setAccountNonLocked(null);
        user.setCredentialsNonExpired(null);
        user.setIsEnabled(null);
        return R.success(user);
    }
}