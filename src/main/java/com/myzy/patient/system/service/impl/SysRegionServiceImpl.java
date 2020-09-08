package com.myzy.patient.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myzy.patient.system.entity.SysDictionary;
import com.myzy.patient.system.entity.SysRegion;
import com.myzy.patient.system.entity.dictionary.TreeDictionaryVO;
import com.myzy.patient.system.entity.region.TreeRegionVO;
import com.myzy.patient.system.mapper.SysRegionMapper;
import com.myzy.patient.system.service.SysRegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * (SysRegion)表服务实现类
 *
 * @author leekejin
 * @since 2020-08-04 15:55:42
 */
@Slf4j
@Service
public class SysRegionServiceImpl extends ServiceImpl<SysRegionMapper, SysRegion> implements SysRegionService {

    @Cacheable(cacheNames = "region", unless = "#result.size()==0")
    @Override
    public List<TreeRegionVO> treeRegion() {
        return convertTree(super.list());
    }

    @Cacheable(cacheNames = "region", unless = "#result.size()==0")
    @Override
    public List<SysRegion> getChild(int parentId) {
        return super.list(Wrappers.lambdaQuery(SysRegion.class).eq(SysRegion::getParentId, parentId));
    }

    private List<TreeRegionVO> convertTree(List<SysRegion> list) {
        // 转成树形数据
        List<TreeRegionVO> treeRegions = list.stream()
                .map(item -> {
                    TreeRegionVO treeRegionVO = new TreeRegionVO();
                    BeanUtils.copyProperties(item, treeRegionVO);
                    return treeRegionVO;
                }).collect(Collectors.toList());
        // 遍历每个节点，并设置子节点
        treeRegions.forEach(item -> {
            List<TreeRegionVO> childMenus = treeRegions.stream()
                    .filter(child -> child.getParentId().equals(item.getId()))
                    .collect(Collectors.toList());
            if (!childMenus.isEmpty()) {
                item.setChildren(childMenus);
            }
        });
        // 返回省份数据
        return treeRegions.stream()
                .filter(item -> item.getLevel() == 1)
                .collect(Collectors.toList());
    }

}