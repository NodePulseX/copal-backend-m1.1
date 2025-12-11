package com.wangxinye.copal.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户角色关联表（sys_user_role）
 */
@Data
@TableName("sys_user_role")
public class SysUserRole {
    @TableId(type = IdType.AUTO)
    private Long id;              // 主键ID
    private Long userId;          // 用户ID（对应user_id）
    private Long roleId;          // 角色ID（对应role_id）
    private LocalDateTime createTime;  // 关联创建时间（对应create_time）
    private Long createBy;        // 创建人ID（对应create_by）
}