package com.myzy.patient.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myzy.patient.core.entity.OneToManyVO;
import com.myzy.patient.core.entity.Result;
import com.myzy.patient.system.entity.SysRole;
import com.myzy.patient.system.entity.role.CreateRoleVO;
import com.myzy.patient.system.entity.role.QueryRolePage;
import com.myzy.patient.system.entity.role.UpdateRoleVO;
import com.myzy.patient.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * @author leekejin
 * @since 2020-06-16 09:42:49
 */
//@RestController
//@RequestMapping("sysRole")
//@Api(tags = "角色管理", hidden = true)
public class SysRoleController {
    /**
     * 服务对象
     */
    @Resource
    private SysRoleService sysRoleService;

    @ApiOperation("分页查询所有数据")
    @GetMapping
    public Page<SysRole> queryPage(QueryRolePage entity) {
        return sysRoleService.queryPage(entity);
    }

    @ApiOperation("通过主键查询单条数据")
    @GetMapping("{id}")
    public SysRole queryOne(@PathVariable Serializable id) {
        return sysRoleService.getById(id);
    }

    @ApiOperation("新增数据")
    @PostMapping
    public Result add(@Valid @RequestBody CreateRoleVO entity) {
        sysRoleService.add(entity);
        return Result.ok();
    }

    @ApiOperation("修改数据")
    @PutMapping
    public Result update(@Valid @RequestBody UpdateRoleVO entity) {
        sysRoleService.update(entity);
        return Result.ok();
    }

    @ApiOperation("删除数据")
    @DeleteMapping
    public Result delete(@RequestParam("ids") List<Integer> ids) {
        sysRoleService.delete(ids);
        return Result.ok();
    }

    @ApiOperation("分配用户")
    @PostMapping("assignUsers")
    public Result assignUsers(@Valid @RequestBody OneToManyVO entity) {
        sysRoleService.assignUsers(entity);
        return Result.ok();
    }

    @ApiOperation("获取角色关联的用户")
    @GetMapping("getRelationUsers")
    public List<Integer> getRelationUsers(int roleId) {
        return sysRoleService.getRelationUsers(roleId);
    }


    @ApiOperation("分配菜单")
    @PostMapping("assignMenus")
    public Result assignMenus(@Valid @RequestBody OneToManyVO entity) {
        sysRoleService.assignMenus(entity);
        return Result.ok();
    }

    @ApiOperation("获取角色关联的菜单")
    @GetMapping("getRelationMenus")
    public List<Integer> getRelationMenus(int roleId) {
        return sysRoleService.getRelationMenus(roleId);
    }

}