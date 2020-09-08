package com.myzy.patient.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myzy.patient.system.entity.SysUser;
import com.myzy.patient.system.entity.user.QueryUserPageVO;
import com.myzy.patient.system.entity.user.ShowUserPageVO;
import org.apache.ibatis.annotations.Param;

/**
 * 用户表(SysUser)表数据库访问层
 *
 * @author leekejin
 * @since 2020-06-01 17:40:07
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 分页查询数据
     *
     * @param page   分页信息
     * @param query 查询条件
     * @return 分页数据
     */
    Page<ShowUserPageVO> queryPage(Page page, @Param("query") QueryUserPageVO query);
}