package com.wangxinye.copal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 权限表（sys_permission）
 */
@Data
@TableName("sys_permission")
public class SysPermission {
    @TableId(type = IdType.AUTO)
    private Long permissionId;    // 权限唯一标识（对应permission_id）
    private String permissionCode;  // 权限编码（如project:read，对应permission_code）
    private String permissionName;  // 权限名称（如查询项目，对应permission_name）
    private String permissionDescription;  // 权限描述（对应permission_description）
    private String permissionType;  // 权限类型（MENU/FUNCTION/DATA，对应permission_type）
    private String url;           // 权限URL（对应url）
    private String method;        // 请求方法（GET/POST等，对应method）
    private Boolean status;       // 状态（1正常/0禁用，对应status）
    private LocalDateTime createTime;  // 创建时间（对应create_time）
    private LocalDateTime updateTime;  // 更新时间（对应update_time）
}