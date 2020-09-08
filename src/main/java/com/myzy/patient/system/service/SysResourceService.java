package com.myzy.patient.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myzy.patient.core.entity.Result;
import com.myzy.patient.system.entity.SysResource;

import javax.servlet.http.HttpServletRequest;

/**
 * API资源表(SysResource)表服务接口
 *
 * @author leekejin
 * @since 2020-06-02 10:05:11
 */
public interface SysResourceService extends IService<SysResource> {

    /**
     * 校验权限
     *
     * @param request 请求
     * @return 是否拥有权限
     */
    boolean checkAuthorization(HttpServletRequest request);

    /**
     * 按swagger文档初始化资源
     *
     * @return 结果信息
     */
    Result<String> initBySwagger();

}