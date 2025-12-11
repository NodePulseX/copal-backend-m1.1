package com.wangxinye.copal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色权限关联表（sys_role_permission）
 */
@Data
@TableName("sys_role_permission")
public class SysRolePermission {
    @TableId(type = IdType.AUTO)
    private Long id;              // 主键ID
    private Long roleId;          // 角色ID（对应role_id）
    private Long permissionId;    // 权限ID（对应permission_id）
    private LocalDateTime createTime;  // 关联创建时间（对应create_time）
    private Long createBy;        // 创建人ID（对应create_by）
}