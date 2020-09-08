package com.myzy.patient.patient.entity.patientInfo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 字段信息
 * @author leekejin
 * @since 2020-8-15 10:30:36
 */
@Data
public class FieldInfoVO {

    @ApiModelProperty(value = "字段名")
    private String fieldName;
    @ApiModelProperty(value = "前端应该展示的名称,中文")
    private String showName;
    @ApiModelProperty(value = "前端页面的表格id，患者管理的表格暂列为1,这里可以暂时不考虑",hidden = true)
    private String viewId;
    @ApiModelProperty(value = "是否默认，0-非默认，1-默认")
    private Integer isDefault;
    @ApiModelProperty(value = "分组名")
    private String groupName;
}
