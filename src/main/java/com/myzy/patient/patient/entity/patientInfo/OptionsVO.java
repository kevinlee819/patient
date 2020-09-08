package com.myzy.patient.patient.entity.patientInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 选项信息
 *
 * @author leekejin
 * @since 2020-8-10 9:30:21
 */
@Data
public class OptionsVO {
    @ApiModelProperty(value = "一个字段对应的若干选项")
    private Map<String, List<OptionVO>> options;
}
