package com.myzy.patient.system.controller;

import com.myzy.patient.core.UserContext;
import com.myzy.patient.core.entity.Result;
import com.myzy.patient.core.entity.TokenEntity;
import com.myzy.patient.system.entity.SysMenu;
import com.myzy.patient.system.entity.menu.CreateMenuVO;
import com.myzy.patient.system.entity.menu.TreeMenuVO;
import com.myzy.patient.system.entity.menu.UpdateMenuVO;
import com.myzy.patient.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * @author leekejin
 * @since 2020-06-03 09:59:35
 */
@RestController
@RequestMapping("sysMenu")
@Api(tags = "菜单管理", hidden = true)
public class SysMenuController {
    /**
     * 服务对象
     */
    @Resource
    private SysMenuService sysMenuService;

    @ApiOperation("获取当前用户菜单(树形菜单)")
    @GetMapping("currentUserMenu")
    public List<TreeMenuVO> currentUserMenu() {
        TokenEntity tokenEntity = UserContext.getUser();
        return sysMenuService.currentMenu(tokenEntity);
    }

    @ApiOperation("获取全部菜单(树形菜单)")
    @GetMapping("allMenu")
    public List<TreeMenuVO> allMenu() {
        return sysMenuService.allMenu();
    }

    @ApiOperation("通过主键查询单条数据")
    @GetMapping("{id}")
    public SysMenu queryOne(@PathVariable Serializable id) {
        return sysMenuService.getById(id);
    }

    @ApiOperation("新增数据")
    @PostMapping
    public Result add(@Valid @RequestBody CreateMenuVO entity) {
        sysMenuService.add(entity);
        return Result.ok();
    }

    @ApiOperation("修改数据")
    @PutMapping
    public Result update(@Valid @RequestBody UpdateMenuVO entity) {
        sysMenuService.update(entity);
        return Result.ok();
    }

    @ApiOperation("删除数据")
    @DeleteMapping
    public Result delete(@RequestParam("ids") List<Integer> ids) {
        sysMenuService.delete(ids);
        return Result.ok();
    }

}