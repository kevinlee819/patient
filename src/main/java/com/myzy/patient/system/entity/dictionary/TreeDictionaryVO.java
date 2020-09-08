package com.myzy.patient.system.entity.dictionary;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 树形菜单
 *
 * @author leekejin
 * @date 2020-06-03 10:03
 */
@Data
public class TreeDictionaryVO {

    @ApiModelProperty(value = "字典id")
    private Integer id;

    @ApiModelProperty(value = "父节点id")
    private Integer parentId;

    @ApiModelProperty(value = "层级")
    private Integer level;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "代码")
    private String code;

    @ApiModelProperty(value = "值")
    private String value;

    @ApiModelProperty(value = "描述信息")
    private String description;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "状态：0正常、1停用")
    private Integer status;

    @ApiModelProperty(value = "子菜单")
    private List<TreeDictionaryVO> children;

}
