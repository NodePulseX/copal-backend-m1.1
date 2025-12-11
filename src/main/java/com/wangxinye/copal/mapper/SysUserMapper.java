package com.wangxinye.copal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangxinye.copal.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 用户数据访问接口（注解式SQL，无XML）
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据用户名查询用户（含状态校验）
     * 替代原来的 SysUserMapper.xml 中的 selectByUsername
     */
    @Select("""
        SELECT
            user_id, username, password, email, nick_name, phone, avatar, dept_id,
            login_count, is_enabled, account_non_expired, credentials_non_expired, account_non_locked
        FROM sys_user
        WHERE username = #{username}
          AND is_enabled = 1
          AND account_non_expired = 1
          AND credentials_non_expired = 1
          AND account_non_locked = 1
        """)
    SysUser selectByUsername(@Param("username") String username);
}