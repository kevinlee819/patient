package com.myzy.patient.system.entity.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 角色表修改实体
 *
 * @author leekejin
 * @date 2020-06-16 9:19
 */
@Data
public class UpdateRoleVO {

    @NotNull(message = "角色id不能为空")
    @ApiModelProperty(value = "id", required = true)
    private Integer id;

    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty(value = "角色名称", required = true)
    private String name;

    @ApiModelProperty(value = "角色描述")
    private String description;

    @Range(max = 1, message = "状态只能为0 - 1")
    @ApiModelProperty(value = "状态：0正常、1停用", required = true)
    private Integer status;

}
