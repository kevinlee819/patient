package com.myzy.patient.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myzy.patient.system.entity.SysRegion;
import com.myzy.patient.system.entity.region.TreeRegionVO;

import java.util.List;

/**
 * (SysRegion)表服务接口
 *
 * @author leekejin
 * @since 2020-08-04 15:55:42
 */
public interface SysRegionService extends IService<SysRegion> {

    /**
     * 查询行政区划列表-返回树形数据
     *
     * @return 行政区划列表
     */
    List<TreeRegionVO> treeRegion();

    /**
     * 通过上级id查询子列表
     *
     * @param parentId 父节点id
     * @return 子列表
     */
    List<SysRegion> getChild(int parentId);

}