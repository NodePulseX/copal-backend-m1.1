package com.wangxinye.copal.dto;

import jakarta.validation.constraints.NotBlank; // 修正：从jakarta导入，不是javax
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}