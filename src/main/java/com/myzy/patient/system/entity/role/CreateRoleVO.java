package com.myzy.patient.system.entity.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 角色表创建实体
 *
 * @author leekejin
 * @date 2020-06-16 9:19
 */
@Data
public class CreateRoleVO {

    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty(value = "角色名称", required = true)
    private String name;

    @ApiModelProperty(value = "角色描述")
    private String description;

}
