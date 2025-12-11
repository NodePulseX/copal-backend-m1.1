package com.wangxinye.copal.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.wangxinye.copal.entity.SysPermission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限数据访问接口（注解式SQL，无XML）
 */
@Repository
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     * 根据角色ID列表查询关联的权限列表
     * 替代原来的 SysPermissionMapper.xml 中的 selectByRoleIds
     * 注意：注解中使用 <script> 包裹，支持 MyBatis 动态SQL（foreach）
     */
    @Select({
            "<script>",
            "SELECT",
            "    p.permission_id, p.permission_code, p.permission_name, p.permission_type, p.status",
            "FROM sys_permission p",
            "LEFT JOIN sys_role_permission rp ON p.permission_id = rp.permission_id",
            "WHERE rp.role_id IN",
            "<foreach collection='roleIds' item='roleId' open='(' separator=',' close=')'>",
            "    #{roleId}",
            "</foreach>",
            "  AND p.status = 1",
            "</script>"
    })
    List<SysPermission> selectByRoleIds(@Param("roleIds") List<Long> roleIds);
}