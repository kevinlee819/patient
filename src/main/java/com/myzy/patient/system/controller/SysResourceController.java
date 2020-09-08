package com.myzy.patient.system.controller;

import com.myzy.patient.core.entity.Result;
import com.myzy.patient.system.service.SysResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author leekejin
 * @since 2020-06-02 10:05:11
 */
//@RestController
//@RequestMapping("sysResource")
//@Api(tags = "资源管理", hidden = true)
public class SysResourceController {

    @Resource
    private SysResourceService sysResourceService;

    @ApiOperation("按swagger文档初始化资源")
    @GetMapping("/initBySwagger")
    public Result<String> initBySwagger() {
        return sysResourceService.initBySwagger();
    }

}