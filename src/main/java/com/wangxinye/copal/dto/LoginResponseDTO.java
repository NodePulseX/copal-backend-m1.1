package com.wangxinye.copal.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class LoginResponseDTO {
    private String token;
    private UserInfoDTO userInfo;
    private List<String> roles;
    private List<String> permissions;

    @Data
    @Builder
    public static class UserInfoDTO {
        private Long userId;
        private String username;
        private String nickName;
        private String email;
        private String phone;
        private String avatar;
        private Long deptId;
        private Integer loginCount;
    }
}