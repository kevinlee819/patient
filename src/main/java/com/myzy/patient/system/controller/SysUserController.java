package com.myzy.patient.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myzy.patient.core.UserContext;
import com.myzy.patient.core.entity.Result;
import com.myzy.patient.system.entity.SysUser;
import com.myzy.patient.system.entity.user.*;
import com.myzy.patient.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * @author leekejin
 * @since 2020-06-01 17:40:07
 */
@RestController
@RequestMapping("sysUser")
@Api(tags = "用户管理")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @ApiOperation("用户登录")
    @PostMapping("login")
    public Result<String> login(@Valid @RequestBody UserLoginVO entity) {
        return Result.ok(sysUserService.login(entity));
    }

    @ApiOperation("获取当前用户信息")
    @GetMapping("currentUser")
    public CurrentUserVO currentUser() {
        return sysUserService.currentUser();
    }

    @ApiOperation("刷新token")
    @GetMapping("refreshToken")
    public Result<String> refreshToken() {
        String token = sysUserService.generateToken(UserContext.getUser().getUserId());
        return Result.ok(token);
    }

    @ApiOperation("修改个人密码")
    @PutMapping("password")
    public Result<String> updatePassword(@Valid @RequestBody UpdatePasswordVO entity) {
        sysUserService.updatePassword(entity);
        return Result.ok("ok");
    }

    @ApiOperation("重置密码")
    @PutMapping("resetPassword")
    public Result<String> resetPassword(@Valid @RequestBody ResetPasswordVO entity) {
        sysUserService.resetPassword(entity);
        return Result.ok("ok");
    }

    @ApiOperation("分页查询")
    @GetMapping
    public Page<ShowUserPageVO> queryPage(QueryUserPageVO entity) {
        return sysUserService.queryPage(entity);
    }

    @ApiOperation("通过主键查询单条数据")
    @GetMapping("{id}")
    public SysUser queryOne(@PathVariable Serializable id) {
        return sysUserService.getById(id);
    }

    @ApiOperation("新增数据")
    @PostMapping
    public Result add(@Valid @RequestBody CreateUserVO entity) {
        sysUserService.add(entity);
        return Result.ok();
    }

    @ApiOperation("修改数据")
    @PutMapping
    public Result update(@Valid @RequestBody UpdateUserVO entity) {
        sysUserService.update(entity);
        return Result.ok();
    }

    @ApiOperation("删除数据")
    @DeleteMapping
    public Result delete(@RequestParam("ids") List<Integer> ids) {
        sysUserService.delete(ids);
        return Result.ok();
    }

}