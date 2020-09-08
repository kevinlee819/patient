package com.myzy.patient.system.entity.region;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author leekejin
 * @date 2020-08-04 15:58
 */
@Data
public class TreeRegionVO {

    @ApiModelProperty(value = "id")
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

    @ApiModelProperty(value = "子菜单")
    private List<TreeRegionVO> children;
}
