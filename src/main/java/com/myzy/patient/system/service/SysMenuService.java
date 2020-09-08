package com.myzy.patient.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myzy.patient.core.entity.TokenEntity;
import com.myzy.patient.system.entity.SysMenu;
import com.myzy.patient.system.entity.menu.CreateMenuVO;
import com.myzy.patient.system.entity.menu.TreeMenuVO;
import com.myzy.patient.system.entity.menu.UpdateMenuVO;

import java.util.List;

/**
 * 功能菜单表(SysMenu)表服务接口
 *
 * @author leekejin
 * @since 2020-06-03 09:59:35
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获取当前用户拥有的菜单列表
     *
     * @param tokenEntity 用户信息
     * @return 树形菜单
     */
    List<TreeMenuVO> currentMenu(TokenEntity tokenEntity);

    /**
     * 获取全部菜单
     *
     * @return 树形菜单
     */
    List<TreeMenuVO> allMenu();

    /**
     * 新建菜单
     *
     * @param entity 创建菜单实体
     */
    void add(CreateMenuVO entity);

    /**
     * 修改菜单
     *
     * @param entity 修改菜单实体
     */
    void update(UpdateMenuVO entity);

    /**
     * 删除菜单
     *
     * @param ids 需要删除的id列表
     */
    void delete(List<Integer> ids);

}