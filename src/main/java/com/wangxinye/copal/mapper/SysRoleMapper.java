package com.wangxinye.copal.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.wangxinye.copal.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色数据访问接口（注解式SQL，无XML）
 */
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据用户ID查询关联的角色列表
     * 替代原来的 SysRoleMapper.xml 中的 selectByUserId
     */
    @Select("""
        SELECT
            r.role_id, r.role_code, r.role_name, r.role_description, r.data_scope, r.status
        FROM sys_role r
        LEFT JOIN sys_user_role ur ON r.role_id = ur.role_id
        WHERE ur.user_id = #{userId}
          AND r.status = 1
        """)
    List<SysRole> selectByUserId(@Param("userId") Long userId);
}



