package com.myzy.patient.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myzy.patient.core.Constant;
import com.myzy.patient.core.UserContext;
import com.myzy.patient.core.annotation.LogPoint;
import com.myzy.patient.core.entity.TokenEntity;
import com.myzy.patient.core.exception.BusinessException;
import com.myzy.patient.core.exception.HttpStatusCodeEnum;
import com.myzy.patient.system.entity.SysFile;
import com.myzy.patient.system.entity.SysRole;
import com.myzy.patient.system.entity.SysUser;
import com.myzy.patient.system.entity.file.QueryRelationFileVO;
import com.myzy.patient.system.entity.file.RelationFileVO;
import com.myzy.patient.system.entity.user.*;
import com.myzy.patient.system.mapper.SysUserMapper;
import com.myzy.patient.system.service.SysFileService;
import com.myzy.patient.system.service.SysRoleService;
import com.myzy.patient.system.service.SysUserService;
import com.myzy.patient.utils.JwtTokenUtil;
import com.myzy.patient.utils.Sm3Utils;
import com.myzy.patient.system.entity.user.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(SysUser)表服务实现类
 *
 * @author leekejin
 * @since 2020-06-01 17:40:06
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private SysRoleService sysRoleService;

    @LogPoint(message = "'用户登录：'+#entity.getUserName()")
    @Override
    public String login(UserLoginVO entity) {
        // 查询用户信息
        SysUser sysUser = super.getOne(Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getUserName, entity.getUserName())
                .ne(SysUser::getStatus, Constant.DELETE_STATUS));
        if (sysUser == null) {
            throw new BusinessException("用户不存在", HttpStatusCodeEnum.BAD_REQUEST);
        } else if (!Sm3Utils.verification(entity.getPassword(), sysUser.getPassword())) {
            throw new BusinessException("密码错误", HttpStatusCodeEnum.BAD_REQUEST);
        } else if (sysUser.getStatus().equals(1)) {
            throw new BusinessException("用户已被停用", HttpStatusCodeEnum.BAD_REQUEST);
        }
        // 生成并返回token
        return generateToken(sysUser.getId());
    }

    @Override
    public String generateToken(Integer userId) {
        SysUser sysUser = super.getById(userId);
        // 查询角色信息
        List<SysRole> sysRoles = sysRoleService.getRoleByUserId(sysUser.getId());
        String roleIds = sysRoles.stream()
                .map(item -> String.valueOf(item.getId()))
                .collect(Collectors.joining(","));
        //查询创建者
        Integer createrId = sysUser.getCreateUser();
        String createUserName = null;
        if(createrId != null){
        createUserName = super.getById(createrId).getUserName();
        }

        // 返回token
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setUserId(sysUser.getId());
        tokenEntity.setUserName(sysUser.getUserName());
        tokenEntity.setFullName(sysUser.getFullName());
        tokenEntity.setRoleIds(roleIds);
        tokenEntity.setCreateUserName(createUserName);
        String token;
        try {
            token = JwtTokenUtil.generate(tokenEntity);
        } catch (Exception e) {
            log.error("创建token失败:", e);
            throw new BusinessException("创建token失败");
        }
        return token;
    }

    @Override
    public CurrentUserVO currentUser() {
        TokenEntity tokenEntity = UserContext.getUser();
        CurrentUserVO currentUserVO = new CurrentUserVO();
        SysUser user = super.getById(tokenEntity.getUserId());
        BeanUtils.copyProperties(user, currentUserVO);
        String creater = null;
        if (user.getCreateUser() != null){
            creater = super.getById(user.getCreateUser()).getUserName();
        }
        if (creater == null || creater.equals(Constant.ADMIN)) {
            currentUserVO.setAuth(1);
        } else {
            currentUserVO.setAuth(0);
        }
        return currentUserVO;
    }

    @LogPoint(message = "'修改密码：'+#tokenEntity")
    @Override
    public void updatePassword(UpdatePasswordVO entity) {
        TokenEntity tokenEntity = UserContext.getUser();
        SysUser sysUser = super.getById(tokenEntity.getUserId());
        if (sysUser == null || sysUser.getStatus().equals(Constant.DELETE_STATUS)) {
            throw new BusinessException("用户不存在", HttpStatusCodeEnum.BAD_REQUEST);
        } else if (!Sm3Utils.verification(entity.getOldPassword(), sysUser.getPassword())) {
            throw new BusinessException("旧密码错误", HttpStatusCodeEnum.BAD_REQUEST);
        } else if (sysUser.getStatus().equals(1)) {
            throw new BusinessException("用户已被停用", HttpStatusCodeEnum.BAD_REQUEST);
        }
        sysUser.setPassword(Sm3Utils.encryption(entity.getNewPassword()));
        sysUser.setUpdateTime(new Date());
        super.updateById(sysUser);
    }

    @LogPoint(message = "'重置密码：'+#tokenEntity+',修改列表：'+#entity.getIds()")
    @Override
    public void resetPassword(ResetPasswordVO entity) {
        super.update(Wrappers.lambdaUpdate(SysUser.class)
                .in(SysUser::getId, entity.getIds())
                .ne(SysUser::getUserName, Constant.ADMIN)
                .set(SysUser::getPassword, Sm3Utils.encryption(entity.getPassword()))
                .set(SysUser::getUpdateTime, new Date()));
    }

    @Override
    public Page<ShowUserPageVO> queryPage(QueryUserPageVO entity) {
        Page page = entity.genPage().addOrder(OrderItem.desc("id"));
        return baseMapper.queryPage(page, entity);
    }

    @Override
    public void add(CreateUserVO entity) {
        // 新增用户
        int count = super.count(Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getUserName, entity.getUserName())
                .ne(SysUser::getStatus, Constant.DELETE_STATUS));
        if (count > 0) {
            throw new BusinessException("用户名已存在");
        }
        int commonNum = super.count(Wrappers.lambdaQuery(SysUser.class)
                .ne(SysUser::getCreateUser, super.getById(UserContext.getUser().getUserId()).getCreateUser())
                .ne(SysUser::getStatus, Constant.DELETE_STATUS));
        //普通用户不允许超过1500个，多出这一个是admin，其创建者为null，所以在这里一并统计
        if (commonNum >= 1501) {
            throw new BusinessException("用户数量不允许超过1500个");
        }
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(entity, sysUser);
        sysUser.setPassword(Sm3Utils.encryption(sysUser.getPassword()));
        sysUser.setStatus(0);
        sysUser.setCreateUser(UserContext.getUser().getUserId());
        sysUser.setCreateTime(new Date());
        super.save(sysUser);
    }

    @Override
    public void update(UpdateUserVO entity) {
        SysUser sysUser = super.getById(entity.getId());
        if (sysUser == null) {
            throw new BusinessException("用户不存在");
        }
        BeanUtils.copyProperties(entity, sysUser);
        sysUser.setUpdateTime(new Date());
        super.updateById(sysUser);
    }

    @LogPoint(message = "'删除用户信息：'+#tokenEntity+',删除列表：'+#ids")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<Integer> ids) {
        ids.forEach(id -> {
            SysUser sysUser = super.getById(id);
            if (sysUser == null) {
                log.warn("用户id{}不存在", id);
                throw new BusinessException("用户不存在");
            } else if (Constant.ADMIN.equals(sysUser.getUserName())) {
                throw new BusinessException("admin账号不允许删除");
            }
            // 逻辑删除
            boolean updateFlag = super.update(Wrappers.lambdaUpdate(SysUser.class)
                    .eq(SysUser::getId, id)
                    .set(SysUser::getStatus, Constant.DELETE_STATUS));
            if (!updateFlag) {
                throw new BusinessException("删除用户失败");
            }
        });
    }
}