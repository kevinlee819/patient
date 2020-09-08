package com.myzy.patient.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.myzy.patient.system.entity.SysUser;
import com.myzy.patient.system.entity.user.*;
import com.myzy.patient.system.entity.user.*;

import java.util.List;

/**
 * 用户表(SysUser)表服务接口
 *
 * @author leekejin
 * @since 2020-06-01 17:40:05
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 用户登录
     *
     * @param entity 登录参数
     * @return JWT Token字符串
     */
    String login(UserLoginVO entity);

    /**
     * 通过用户id生成包含用户信息的Jwt Token
     * @param userId 用户id
     * @return token
     */
    String generateToken(Integer userId);

    /**
     * 获取当前用户信息
     *
     * @return 用户信息
     */
    CurrentUserVO currentUser();

    /**
     * 修改用户密码
     *
     * @param entity 修改密码参数
     */
    void updatePassword(UpdatePasswordVO entity);

    /**
     * 重置密码
     *
     * @param entity 重置密码参数
     */
    void resetPassword(ResetPasswordVO entity);

    /**
     * 分页查询数据
     *
     * @param entity 查询条件
     * @return 分页数据
     */
    Page<ShowUserPageVO> queryPage(QueryUserPageVO entity);

    /**
     * 新建用户
     *
     * @param entity 创建用户实体
     */
    void add(CreateUserVO entity);

    /**
     * 修改用户
     *
     * @param entity 修改用户实体
     */
    void update(UpdateUserVO entity);

    /**
     * 删除用户
     *
     * @param ids 需要删除的id列表
     */
    void delete(List<Integer> ids);

}