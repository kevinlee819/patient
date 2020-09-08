package com.myzy.patient.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色菜单关联表实体
 *
 * @author leekejin
 * @since 2020-06-23 18:02:15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRoleMenu extends Model<SysRoleMenu> {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "角色id")
    private Integer roleId;

    @ApiModelProperty(value = "菜单id")
    private Integer menuId;

}