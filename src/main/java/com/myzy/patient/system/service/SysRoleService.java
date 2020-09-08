package com.myzy.patient.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.myzy.patient.core.entity.OneToManyVO;
import com.myzy.patient.system.entity.SysRole;
import com.myzy.patient.system.entity.role.CreateRoleVO;
import com.myzy.patient.system.entity.role.QueryRolePage;
import com.myzy.patient.system.entity.role.UpdateRoleVO;

import java.util.List;

/**
 * 角色表(SysRole)表服务接口
 *
 * @author leekejin
 * @since 2020-06-03 11:04:38
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 获取角色列表
     *
     * @param userId 角色id
     * @return 角色列表
     */
    List<SysRole> getRoleByUserId(Integer userId);

    /**
     * 分页查询数据
     *
     * @param entity 查询条件
     * @return 分页数据
     */
    Page<SysRole> queryPage(QueryRolePage entity);

    /**
     * 新增
     *
     * @param entity 创建实体
     */
    void add(CreateRoleVO entity);

    /**
     * 修改
     *
     * @param entity 修改实体
     */
    void update(UpdateRoleVO entity);

    /**
     * 批量删除
     *
     * @param ids 需要删除的id列表
     */
    void delete(List<Integer> ids);

    /**
     * 分配用户
     *
     * @param entity 一对多关联实体
     */
    void assignUsers(OneToManyVO entity);

    /**
     * 获取角色关联的用户
     *
     * @param roleId 角色id
     * @return 用户id列表
     */
    List<Integer> getRelationUsers(int roleId);

    /**
     * 分配菜单
     *
     * @param entity 一对多关联实体
     */
    void assignMenus(OneToManyVO entity);

    /**
     * 获取角色关联的菜单
     *
     * @param roleId 角色id
     * @return 用户id列表
     */
    List<Integer> getRelationMenus(int roleId);

}