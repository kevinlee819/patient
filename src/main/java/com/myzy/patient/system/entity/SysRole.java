package com.myzy.patient.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色表
 *
 * @author leekejin
 * @since 2020-06-03 11:04:38
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRole extends Model<SysRole> {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色描述")
    private String description;

    @ApiModelProperty(value = "状态：0正常、1停用、9删除")
    private Integer status;

}