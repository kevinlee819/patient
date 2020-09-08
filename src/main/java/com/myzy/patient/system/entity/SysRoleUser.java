package com.myzy.patient.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色用户关联表实体
 *
 * @author leekejin
 * @since 2020-06-23 16:39:35
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRoleUser extends Model<SysRoleUser> {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "角色id")
    private Integer roleId;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

}