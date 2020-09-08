package com.myzy.patient.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myzy.patient.system.entity.SysRoleUser;

import java.util.List;

/**
 * 角色用户关联表(SysRoleUser)表数据库访问层
 *
 * @author leekejin
 * @since 2020-06-23 16:39:35
 */
public interface SysRoleUserMapper extends BaseMapper<SysRoleUser> {

    /**
     * 批量保存数据
     *
     * @param list 数据列表
     * @return 成功条数
     */
    int batchSave(List<SysRoleUser> list);

}