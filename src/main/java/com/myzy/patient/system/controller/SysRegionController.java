package com.myzy.patient.system.controller;

import com.myzy.patient.system.entity.SysRegion;
import com.myzy.patient.system.entity.region.TreeRegionVO;
import com.myzy.patient.system.service.SysRegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author leekejin
 * @since 2020-08-04 15:55:42
 */
@RestController
@RequestMapping("sysRegion")
@Api(tags = "行政区划管理")
public class SysRegionController {

    @Resource
    private SysRegionService sysRegionService;

    @ApiOperation(value = "查询行政区划列表-返回树形数据")
    @GetMapping
    public List<TreeRegionVO> treeRegion() {
        return sysRegionService.treeRegion();
    }

    @ApiOperation(value = "通过父节点id查询子列表")
    @GetMapping("/getChild")
    public List<SysRegion> getChild(int parentId) {
        return sysRegionService.getChild(parentId);
    }

}