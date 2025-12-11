package com.wangxinye.copal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * 系统用户表（sys_user）
 */
@Data
@TableName("sys_user")  // 对应数据库表名
public class SysUser implements UserDetails {
    @TableId(type = IdType.AUTO)  // 主键自增（适配数据库AUTO_INCREMENT）
    private Long userId;          // 用户唯一标识（对应user_id）
    private String username;      // 用户名（登录用，对应username）
    private String password;      // 加密后的密码（对应password）
    private String email;         // 邮箱（对应email）
    private String nickName;      // 用户昵称（对应nick_name）
    private String phone;         // 手机号码（对应phone）
    private String avatar;        // 头像URL（对应avatar）
    private Long deptId;          // 所属部门ID（对应dept_id）
    private LocalDateTime lastLoginTime;  // 最后登录时间（对应last_login_time）
    private String loginIp;       // 最后登录IP（对应login_ip）
    private Integer loginCount;   // 登录次数（对应login_count）
    private Boolean isEnabled;    // 账户是否启用（对应is_enabled，1启用/0禁用）
    private Boolean accountNonExpired;  // 账户是否未过期（对应account_non_expired）
    private Boolean credentialsNonExpired;  // 凭证是否未过期（对应credentials_non_expired）
    private Boolean accountNonLocked;  // 账户是否未锁定（对应account_non_locked）
    private LocalDateTime passwordResetTime;  // 密码重置时间（对应password_reset_time）
    private LocalDateTime passwordExpireTime;  // 密码过期时间（对应password_expire_time）
    private LocalDateTime createTime;  // 创建时间（对应create_time）
    private LocalDateTime updateTime;  // 更新时间（对应update_time）
    private Long createBy;        // 创建人ID（对应create_by）
    private Long updateBy;        // 更新人ID（对应update_by）

    // 扩展字段：用户关联的角色列表（非数据库字段，登录时加载）
    private transient List<SysRole> roles;
    // 扩展字段：用户关联的权限列表（非数据库字段，登录时加载）
    private transient List<SysPermission> permissions;

    /**
     * Spring Security必需：返回用户的权限集合（适配权限校验）
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 把权限编码（如project:read）转为Spring Security的GrantedAuthority
        return permissions.stream()
                .map(permission -> (GrantedAuthority) permission::getPermissionCode)
                .toList();
    }

    /**
     * Spring Security必需：账户是否未过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return Boolean.TRUE.equals(accountNonExpired);
    }

    /**
     * Spring Security必需：账户是否未锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return Boolean.TRUE.equals(accountNonLocked);
    }

    /**
     * Spring Security必需：凭证（密码）是否未过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return Boolean.TRUE.equals(credentialsNonExpired);
    }

    /**
     * Spring Security必需：账户是否启用
     */
    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(isEnabled);
    }
}