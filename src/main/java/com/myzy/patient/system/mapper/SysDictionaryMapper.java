package com.myzy.patient.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myzy.patient.system.entity.SysDictionary;
import com.myzy.patient.system.entity.dictionary.QueryDictionaryVO;

import java.util.List;

/**
 * 通用字典(SysDictionary)表数据库访问层
 *
 * @author leekejin
 * @since 2020-07-09 10:58:36
 */
public interface SysDictionaryMapper extends BaseMapper<SysDictionary> {

    /**
     * 根据条件查询全部数据
     *
     * @param entity 查询条件
     * @return 结果数据
     */
    List<SysDictionary> queryList(QueryDictionaryVO entity);

    /**
     * 查询子字典列表
     *
     * @param code 字典code
     * @return 数据列表
     */
    List<SysDictionary> getChildByCode(String code);

}