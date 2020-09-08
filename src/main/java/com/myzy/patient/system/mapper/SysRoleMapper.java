package com.myzy.patient.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myzy.patient.system.entity.SysRole;
import com.myzy.patient.system.entity.role.QueryRolePage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色表(SysRole)表数据库访问层
 *
 * @author leekejin
 * @since 2020-06-03 11:04:38
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 获取角色列表
     *
     * @param userId 用户id
     * @return 角色列表
     */
    List<SysRole> getRoleByUserId(Integer userId);

    /**
     * 分页查询数据
     *
     * @param page   分页信息
     * @param query 查询条件
     * @return 分页数据
     */
    Page<SysRole> queryPage(Page page, QueryRolePage query);

}