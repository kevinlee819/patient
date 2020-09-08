package com.myzy.patient.system.entity.role;

import com.myzy.patient.core.entity.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色表分页查询
 *
 * @author leekejin
 * @date 2020-06-16 9:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryRolePage extends BasePage {

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "状态：0正常、1停用、9删除")
    private Integer status;

}
