package com.myzy.patient.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myzy.patient.core.Constant;
import com.myzy.patient.core.annotation.LogPoint;
import com.myzy.patient.core.entity.TokenEntity;
import com.myzy.patient.core.exception.BusinessException;
import com.myzy.patient.system.entity.SysMenu;
import com.myzy.patient.system.entity.menu.CreateMenuVO;
import com.myzy.patient.system.entity.menu.TreeMenuVO;
import com.myzy.patient.system.entity.menu.UpdateMenuVO;
import com.myzy.patient.system.mapper.SysMenuMapper;
import com.myzy.patient.system.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 功能菜单表(SysMenu)表服务实现类
 *
 * @author leekejin
 * @since 2020-06-03 09:59:35
 */
@Slf4j
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Cacheable(cacheNames = "menu", unless = "#result.size()==0")
    @Override
    public List<TreeMenuVO> currentMenu(TokenEntity tokenEntity) {
        // 分隔字符串获取角色id列表
        List<Integer> roleIds = Arrays.stream(tokenEntity.getRoleIds().split(","))
                .filter(StringUtils::isNotBlank)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        // 账号为admin可以获取全部菜单
        boolean isAdmin = Constant.ADMIN.equals(tokenEntity.getUserName());
        if (!isAdmin && roleIds.size() == 0) {
            throw new BusinessException("用户未分配角色");
        }
        // 查询菜单信息，roleIds为空查询所有
        List<SysMenu> sysMenus = baseMapper.getMenuByRoles(isAdmin ? null : roleIds);
        // 菜单列表转成树形菜单
        return convertTree(sysMenus);
    }

    @Cacheable(cacheNames = "menu", unless = "#result.size()==0")
    @Override
    public List<TreeMenuVO> allMenu() {
        List<SysMenu> sysMenus = super.list(Wrappers.lambdaQuery(SysMenu.class)
                .ne(SysMenu::getStatus, Constant.DELETE_STATUS));
        return convertTree(sysMenus);
    }

    @CacheEvict(cacheNames = "menu", allEntries = true)
    @Override
    public void add(CreateMenuVO entity) {
        if (entity.getParentId() == null) {
            entity.setParentId(0);
        }
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(entity, sysMenu);
        sysMenu.setStatus(0);
        super.save(sysMenu);
    }

    @CacheEvict(cacheNames = "menu", allEntries = true)
    @Override
    public void update(UpdateMenuVO entity) {
        if (entity.getParentId() == null) {
            entity.setParentId(0);
        }
        if (entity.getParentId().equals(entity.getId())) {
            throw new BusinessException("父级菜单不能为自己");
        }
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(entity, sysMenu);
        super.updateById(sysMenu);
    }

    @LogPoint(message = "'删除菜单信息：'+#tokenEntity+',删除列表：'+#ids")
    @CacheEvict(cacheNames = "menu", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<Integer> ids) {
        ids.forEach(id -> {
            SysMenu sysMenu = super.getById(id);
            if (sysMenu == null) {
                log.warn("菜单id{}不存在", id);
                throw new BusinessException("菜单不存在");
            }
            // 查询出子列表
            List<SysMenu> childList = super.list(Wrappers.lambdaQuery(SysMenu.class)
                    .eq(SysMenu::getParentId, id)
                    .ne(SysMenu::getStatus, Constant.DELETE_STATUS));
            // 子列表在删除id中没有的数据条数（子列表也删除完的话就放过）
            long childCount = childList.stream()
                    .filter(item -> !ids.contains(item.getId()))
                    .count();
            if (childCount > 0) {
                log.warn("菜单id{}存在子菜单，删除失败", id);
                throw new BusinessException("菜单存在子菜单，删除失败");
            }
            // 逻辑删除
            boolean updateFlag = super.update(Wrappers.lambdaUpdate(SysMenu.class)
                    .eq(SysMenu::getId, id)
                    .set(SysMenu::getStatus, Constant.DELETE_STATUS));
            if (!updateFlag) {
                throw new BusinessException("删除菜单失败");
            }
        });
    }

    /**
     * 菜单列表转换为树形（根据parentId递归）
     *
     * @param sysMenus 菜单列表
     * @return 树形菜单
     */
    private List<TreeMenuVO> convertTree(List<SysMenu> sysMenus) {
        // SysMenu转TreeMenuVO
        List<TreeMenuVO> listMenus = sysMenus.stream()
                .map(item -> {
                    TreeMenuVO treeMenuVO = new TreeMenuVO();
                    BeanUtils.copyProperties(item, treeMenuVO);
                    return treeMenuVO;
                }).collect(Collectors.toList());
        // 遍历每个节点，并设置子节点
        listMenus.forEach(item -> {
            List<TreeMenuVO> childMenus = listMenus.stream()
                    .filter(child -> child.getParentId().equals(item.getId()))
                    .collect(Collectors.toList());
            if (!childMenus.isEmpty()) {
                item.setChildren(childMenus);
            }
        });
        // 返回第一级节点的数据
        return listMenus.stream()
                .filter(item -> item.getParentId() == null || item.getParentId().equals(0))
                .collect(Collectors.toList());
    }

}