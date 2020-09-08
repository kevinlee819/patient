package com.myzy.patient.core.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 一对多关联实体
 *
 * @author leekejin
 */
@Data
public class OneToManyVO {

    @NotNull(message = "one不能为空")
    @ApiModelProperty(value = "根据实际情况使用，一般为id", required = true)
    private Integer one;

    @NotNull(message = "many不能为空")
    @ApiModelProperty(value = "根据实际情况使用，多的一方的id列表", required = true)
    private List<Integer> many;

}
