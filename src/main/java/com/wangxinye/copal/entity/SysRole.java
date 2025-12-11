package com.wangxinye.copal.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统角色表（sys_role）
 */
@Data
@TableName("sys_role")
public class SysRole {
    @TableId(type = IdType.AUTO)
    private Long roleId;          // 角色唯一标识（对应role_id）
    private String roleCode;      // 角色编码（如ROLE_ADMIN，对应role_code）
    private String roleName;      // 角色名称（如系统管理员，对应role_name）
    private String roleDescription;  // 角色描述（对应role_description）
    private String dataScope;     // 数据范围（ALL/DEPT/SELF，对应data_scope）
    private Boolean status;       // 状态（1正常/0禁用，对应status）
    private LocalDateTime createTime;  // 创建时间（对应create_time）
    private LocalDateTime updateTime;  // 更新时间（对应update_time）
}
