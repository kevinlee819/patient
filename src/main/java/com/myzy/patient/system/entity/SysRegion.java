package com.myzy.patient.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实体
 *
 * @author leekejin
 * @since 2020-08-04 15:55:42
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRegion extends Model<SysRegion> {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "父节点id")
    private Integer parentId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "等级")
    private Integer level;

    @ApiModelProperty(value = "邮政编码")
    private String postalCode;

    @ApiModelProperty(value = "行政区划代码")
    private String areaNumber;

}