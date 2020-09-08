package com.myzy.patient.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myzy.patient.system.entity.SysDictionary;
import com.myzy.patient.system.entity.dictionary.CreateDictionaryVO;
import com.myzy.patient.system.entity.dictionary.QueryDictionaryVO;
import com.myzy.patient.system.entity.dictionary.TreeDictionaryVO;
import com.myzy.patient.system.entity.dictionary.UpdateDictionaryVO;

import java.util.List;

/**
 * 通用字典(SysDictionary)表服务接口
 *
 * @author leekejin
 * @since 2020-07-09 10:58:36
 */
public interface SysDictionaryService extends IService<SysDictionary> {

    /**
     * 查询字典列表-返回树形数据
     *
     * @param entity 查询条件
     * @return 树形数据
     */
    List<TreeDictionaryVO> treeDictionary(QueryDictionaryVO entity);

    /**
     * 查询子字典列表
     *
     * @param code 字典code
     * @return 数据列表
     */
    List<SysDictionary> getChildByCode(String code);

    /**
     * 新增
     *
     * @param entity 创建实体
     */
    void add(CreateDictionaryVO entity);

    /**
     * 修改
     *
     * @param entity 修改实体
     */
    void update(UpdateDictionaryVO entity);

    /**
     * 批量删除
     *
     * @param ids 需要删除的id列表
     */
    void delete(List<Integer> ids);

}