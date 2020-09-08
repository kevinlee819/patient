package com.myzy.patient.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 功能菜单表
 *
 * @author leekejin
 * @since 2020-06-03 09:59:35
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysMenu extends Model<SysMenu> {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "父节点id：0表示一级节点")
    private Integer parentId;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "html页面路径")
    private String path;

    @ApiModelProperty(value = "排序序号")
    private Integer sort;

    @ApiModelProperty(value = "状态：0正常、1停用、9删除")
    private Integer status;

}