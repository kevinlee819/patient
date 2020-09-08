package com.myzy.patient.patient.entity.patientInfo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 选项信息
 *
 * @author leekejin
 * @since 2020-8-10 9:26:15
 */
@Data
public class OptionVO {

    @ApiModelProperty(value = "文字描述")
    private String description;
    @ApiModelProperty(value = "具体值")
    private String value;
}
