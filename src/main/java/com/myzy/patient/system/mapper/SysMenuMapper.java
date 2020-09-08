package com.myzy.patient.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myzy.patient.system.entity.SysMenu;

import java.util.List;

/**
 * 功能菜单表(SysMenu)表数据库访问层
 *
 * @author leekejin
 * @since 2020-06-03 09:59:35
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 查询角色关联的菜单
     *
     * @param roleIds 角色id列表
     * @return 菜单列表
     */
    List<SysMenu> getMenuByRoles(List<Integer> roleIds);

}