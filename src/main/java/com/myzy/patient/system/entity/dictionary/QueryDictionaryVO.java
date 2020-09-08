package com.myzy.patient.system.entity.dictionary;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通用字典查询
 *
 * @author leekejin
 * @since 2020-07-09 10:58:36
 */
@Data
public class QueryDictionaryVO {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "代码")
    private String code;

    @ApiModelProperty(value = "描述信息")
    private String description;

    @ApiModelProperty(value = "状态：0正常、1停用")
    private Integer status;

}