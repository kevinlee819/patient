package com.myzy.patient.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myzy.patient.core.Constant;
import com.myzy.patient.core.annotation.LogPoint;
import com.myzy.patient.core.exception.BusinessException;
import com.myzy.patient.core.exception.HttpStatusCodeEnum;
import com.myzy.patient.system.entity.SysDictionary;
import com.myzy.patient.system.entity.dictionary.CreateDictionaryVO;
import com.myzy.patient.system.entity.dictionary.QueryDictionaryVO;
import com.myzy.patient.system.entity.dictionary.TreeDictionaryVO;
import com.myzy.patient.system.entity.dictionary.UpdateDictionaryVO;
import com.myzy.patient.system.mapper.SysDictionaryMapper;
import com.myzy.patient.system.service.SysDictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 通用字典(SysDictionary)表服务实现类
 *
 * @author leekejin
 * @since 2020-07-09 10:58:36
 */
@Slf4j
@Service
public class SysDictionaryServiceImpl extends ServiceImpl<SysDictionaryMapper, SysDictionary> implements SysDictionaryService {

    @Cacheable(cacheNames = "dictionary", unless = "#result.size()==0")
    @Override
    public List<TreeDictionaryVO> treeDictionary(QueryDictionaryVO entity) {
        List<SysDictionary> sysDictionaries = baseMapper.queryList(entity);
        return convertTree(sysDictionaries);
    }

    @Cacheable(cacheNames = "dictionary", unless = "#result.size()==0")
    @Override
    public List<SysDictionary> getChildByCode(String code) {
        return baseMapper.getChildByCode(code);
    }

    @CacheEvict(cacheNames = "dictionary", allEntries = true)
    @Override
    public void add(CreateDictionaryVO entity) {
        // 校验数据（code不能重复）
        int codeCount = super.count(Wrappers.lambdaQuery(SysDictionary.class)
                .eq(SysDictionary::getCode, entity.getCode())
                .ne(SysDictionary::getStatus, Constant.DELETE_STATUS));
        if (codeCount > 0) {
            throw new BusinessException("字典代码已存在");
        }
        // 获取层级，父节点层级 + 1
        int level = 0;
        if (entity.getParentId() == null) {
            entity.setParentId(0);
        }
        if (entity.getParentId() > 0) {
            SysDictionary parentDictionary = super.getById(entity.getParentId());
            if (parentDictionary != null) {
                level = parentDictionary.getLevel() + 1;
            }
        }
        SysDictionary sysDictionary = new SysDictionary();
        BeanUtils.copyProperties(entity, sysDictionary);
        sysDictionary.setStatus(0);
        sysDictionary.setLevel(level);
        baseMapper.insert(sysDictionary);
    }

    @CacheEvict(cacheNames = "dictionary", allEntries = true)
    @Override
    public void update(UpdateDictionaryVO entity) {
        // 校验数据（code不能重复）
        int codeCount = super.count(Wrappers.lambdaQuery(SysDictionary.class)
                .eq(SysDictionary::getCode, entity.getCode())
                .ne(SysDictionary::getId, entity.getId())
                .ne(SysDictionary::getStatus, Constant.DELETE_STATUS));
        if (codeCount > 0) {
            throw new BusinessException("字典代码已存在");
        }
        // 修改数据
        SysDictionary sysDictionary = super.getById(entity.getId());
        if (sysDictionary == null) {
            throw new BusinessException("字典不存在", HttpStatusCodeEnum.NOT_FOUND);
        }
        BeanUtils.copyProperties(entity, sysDictionary);
        super.updateById(sysDictionary);
    }

    @CacheEvict(cacheNames = "dictionary", allEntries = true)
    @LogPoint(message = "'删除字典信息：'+#tokenEntity+',删除列表：'+#ids")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<Integer> ids) {
        ids.forEach(id -> {
            SysDictionary sysDictionary = super.getById(id);
            if (sysDictionary == null) {
                log.warn("字典id{}不存在", id);
                throw new BusinessException("字典不存在");
            }
            // 查询出子列表
            List<SysDictionary> childList = super.list(Wrappers.lambdaQuery(SysDictionary.class)
                    .eq(SysDictionary::getParentId, id)
                    .ne(SysDictionary::getStatus, Constant.DELETE_STATUS));
            // 子列表在删除id中没有的数据条数（子列表也删除完的话就放过）
            long childCount = childList.stream()
                    .filter(item -> !ids.contains(item.getId()))
                    .count();
            if (childCount > 0) {
                log.warn("字典id{}存在子字典，删除失败", id);
                throw new BusinessException("字典存在子字典，删除失败");
            }
            // 逻辑删除
            boolean updateFlag = super.update(Wrappers.lambdaUpdate(SysDictionary.class)
                    .eq(SysDictionary::getId, id)
                    .set(SysDictionary::getStatus, Constant.DELETE_STATUS));
            if (!updateFlag) {
                throw new BusinessException("删除字典失败");
            }
        });
    }

    /**
     * 字典列表转换为树形（根据parentId递归）
     *
     * @param sysDictionaries 字典列表
     * @return 树形字典
     */
    private List<TreeDictionaryVO> convertTree(List<SysDictionary> sysDictionaries) {
        // 转成树形数据
        List<TreeDictionaryVO> treeDictionaryList = sysDictionaries.stream()
                .map(item -> {
                    TreeDictionaryVO treeDictionaryVO = new TreeDictionaryVO();
                    BeanUtils.copyProperties(item, treeDictionaryVO);
                    return treeDictionaryVO;
                }).collect(Collectors.toList());
        // 遍历每个节点，并设置子节点
        treeDictionaryList.forEach(item -> {
            List<TreeDictionaryVO> childMenus = treeDictionaryList.stream()
                    .filter(child -> child.getParentId().equals(item.getId()))
                    .collect(Collectors.toList());
            if (!childMenus.isEmpty()) {
                item.setChildren(childMenus);
            }
        });
        // 返回第一级节点的数据（没有上一级的）
        Set<Integer> allIds = sysDictionaries.stream()
                .map(SysDictionary::getId)
                .collect(Collectors.toSet());
        return treeDictionaryList.stream()
                .filter(item -> !allIds.contains(item.getParentId()))
                .collect(Collectors.toList());
    }

}