package com.myzy.patient.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myzy.patient.core.Constant;
import com.myzy.patient.core.annotation.LogPoint;
import com.myzy.patient.core.entity.OneToManyVO;
import com.myzy.patient.system.entity.SysRole;
import com.myzy.patient.system.entity.SysRoleMenu;
import com.myzy.patient.system.entity.SysRoleUser;
import com.myzy.patient.system.entity.role.CreateRoleVO;
import com.myzy.patient.system.entity.role.QueryRolePage;
import com.myzy.patient.system.entity.role.UpdateRoleVO;
import com.myzy.patient.system.mapper.SysRoleMapper;
import com.myzy.patient.system.mapper.SysRoleMenuMapper;
import com.myzy.patient.system.mapper.SysRoleUserMapper;
import com.myzy.patient.system.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色表(SysRole)表服务实现类
 *
 * @author leekejin
 * @since 2020-06-03 11:04:38
 */
@Slf4j
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;
    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysRole> getRoleByUserId(Integer userId) {
        return baseMapper.getRoleByUserId(userId);
    }

    @Override
    public Page<SysRole> queryPage(QueryRolePage entity) {
        Page page = entity.genPage().addOrder(OrderItem.desc("id"));
        return baseMapper.queryPage(page, entity);
    }

    @Override
    public void add(CreateRoleVO entity) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(entity, sysRole);
        sysRole.setStatus(0);
        super.save(sysRole);
    }

    @Override
    public void update(UpdateRoleVO entity) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(entity, sysRole);
        super.updateById(sysRole);
    }

    @CacheEvict(cacheNames = {"user", "role", "menu"})
    @LogPoint(message = "'删除角色信息：'+#tokenEntity+',删除列表：'+#ids")
    @Override
    public void delete(List<Integer> ids) {
        // 删除角色
        super.update(Wrappers.lambdaUpdate(SysRole.class)
                .in(SysRole::getId, ids)
                .set(SysRole::getStatus, Constant.DELETE_STATUS));
        // 删除角色相关的用户
        sysRoleUserMapper.delete(Wrappers.lambdaQuery(SysRoleUser.class)
                .in(SysRoleUser::getRoleId, ids));
        // 删除角色相关的菜单
        sysRoleMenuMapper.delete(Wrappers.lambdaQuery(SysRoleMenu.class)
                .in(SysRoleMenu::getRoleId, ids));
    }

    @CacheEvict(cacheNames = "user")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignUsers(OneToManyVO entity) {
        List<SysRoleUser> sysRoleUsers = sysRoleUserMapper.selectList(Wrappers.lambdaQuery(SysRoleUser.class)
                .eq(SysRoleUser::getRoleId, entity.getOne()));
        // 前端没有传过来的id需要删除
        Set<Integer> deleteIds = sysRoleUsers.stream()
                .filter(item -> !entity.getMany().contains(item.getUserId()))
                .map(SysRoleUser::getId)
                .collect(Collectors.toSet());
        if (deleteIds.size() > 0) {
            sysRoleUserMapper.deleteBatchIds(deleteIds);
        }
        // 保存后端没有的id数据
        Set<Integer> allUserIds = sysRoleUsers.stream()
                .map(SysRoleUser::getUserId)
                .collect(Collectors.toSet());
        List<SysRoleUser> addRoleUsers = entity.getMany().stream()
                .filter(userId -> !allUserIds.contains(userId))
                .map(userId -> {
                    SysRoleUser sysRoleUser = new SysRoleUser();
                    sysRoleUser.setRoleId(entity.getOne());
                    sysRoleUser.setUserId(userId);
                    return sysRoleUser;
                }).collect(Collectors.toList());
        if (addRoleUsers.size() > 0) {
            sysRoleUserMapper.batchSave(addRoleUsers);
        }
    }


    @Override
    public List<Integer> getRelationUsers(int roleId) {
        List<SysRoleUser> sysRoleUsers = sysRoleUserMapper.selectList(Wrappers.lambdaQuery(SysRoleUser.class)
                .eq(SysRoleUser::getRoleId, roleId));
        return sysRoleUsers.stream()
                .map(SysRoleUser::getUserId)
                .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void assignMenus(OneToManyVO entity) {
        List<SysRoleMenu> sysRoleMenus = sysRoleMenuMapper.selectList(Wrappers.lambdaQuery(SysRoleMenu.class)
                .eq(SysRoleMenu::getRoleId, entity.getOne()));
        // 前端没有传过来的id需要删除
        Set<Integer> deleteIds = sysRoleMenus.stream()
                .filter(item -> !entity.getMany().contains(item.getMenuId()))
                .map(SysRoleMenu::getId)
                .collect(Collectors.toSet());
        if (deleteIds.size() > 0) {
            sysRoleMenuMapper.deleteBatchIds(deleteIds);
        }
        // 保存后端没有的id数据
        Set<Integer> allMenuIds = sysRoleMenus.stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toSet());
        List<SysRoleMenu> addRoleMenus = entity.getMany().stream()
                .filter(menuId -> !allMenuIds.contains(menuId))
                .map(menuId -> {
                    SysRoleMenu sysRoleMenu = new SysRoleMenu();
                    sysRoleMenu.setRoleId(entity.getOne());
                    sysRoleMenu.setMenuId(menuId);
                    return sysRoleMenu;
                }).collect(Collectors.toList());
        if (addRoleMenus.size() > 0) {
            sysRoleMenuMapper.batchSave(addRoleMenus);
        }
    }

    @Override
    public List<Integer> getRelationMenus(int roleId) {
        List<SysRoleMenu> sysRoleUsers = sysRoleMenuMapper.selectList(Wrappers.lambdaQuery(SysRoleMenu.class)
                .eq(SysRoleMenu::getRoleId, roleId));
        return sysRoleUsers.stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toList());
    }

}