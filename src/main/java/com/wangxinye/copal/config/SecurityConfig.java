package com.wangxinye.copal.config;

import com.wangxinye.copal.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 6.2+ 适配版配置
 * 适配 Spring Boot 3.2.x 版本
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // 显式开启方法级权限控制（可选，增强安全性）
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    /**
     * 密码编码器（BCrypt强哈希算法）
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证管理器（Spring Security 6.x 推荐通过AuthenticationConfiguration获取）
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * 数据访问认证提供者（Spring Security 6.2+ 推荐构造函数传参）
     * 替代过时的setter方式
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // 1. 使用无参构造创建实例
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // 2. 通过setter配置UserDetailsService和PasswordEncoder
        provider.setUserDetailsService(userDetailsService); // 配置用户服务
        provider.setPasswordEncoder(passwordEncoder());     // 配置密码编码器
        return provider;
    }

    /**
     * 安全过滤链（Spring Security 6.x 函数式配置）
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. 关闭CSRF（前后端分离场景必关）
                .csrf(csrf -> csrf.disable())
                // 2. 禁用HTTP Basic认证（可选，前后端分离用JWT不需要）
                .httpBasic(httpBasic -> httpBasic.disable())
                // 3. 会话管理：无状态（JWT认证核心）
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 4. 授权规则配置
                .authorizeHttpRequests(auth -> auth
                        // 放行登录/登出接口
                        .requestMatchers("/auth/login", "/auth/logout").permitAll()
                        // 可选：放行静态资源、Swagger文档等
                        // .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // 所有其他请求需要认证
                        .anyRequest().authenticated()
                )
                // 5. 注册自定义认证提供者
                .authenticationProvider(authenticationProvider());

        // 注意：如果后续加JWT过滤器，需要在这里添加：
        // http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}