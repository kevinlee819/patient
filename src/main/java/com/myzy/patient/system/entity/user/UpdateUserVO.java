package com.myzy.patient.system.entity.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户表修改实体
 *
 * @author leekejin
 * @date 2020-06-08 9:17
 */
@Data
public class UpdateUserVO {

    @NotNull(message = "用户id不能为空")
    @ApiModelProperty(value = "id", required = true)
    private Integer id;

    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value = "姓名", required = true)
    private String fullName;

    @Range(max = 1, message = "状态只能为0 - 1")
    @ApiModelProperty(value = "状态：0正常、1停用")
    private Integer status;

}
