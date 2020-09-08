package com.myzy.patient.system.entity.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class TreeMenuVO {

    @ApiModelProperty(value = "菜单id")
    private Integer id;

    @JsonIgnore
    @ApiModelProperty(value = "父节点id")
    private Integer parentId;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "html页面路径")
    private String path;

    @ApiModelProperty(value = "子菜单")
    private List<TreeMenuVO> children;

}
