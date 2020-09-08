package com.myzy.patient.patient.entity.patientInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 既往史
 *
 * @author leekejin
 * @since 2020-8-7 10:36:56
 */
@Data
public class PastHistoryVO {

    @ApiModelProperty("内分泌")
    private String nfmxt;

    @ApiModelProperty("生殖")
    private String szxt;

    @ApiModelProperty("呼吸")
    private String hxxt;

    @ApiModelProperty("运动")
    private String ydxt;

    @ApiModelProperty("中枢神经")
    private String zssjxt;

    @ApiModelProperty("消化")
    private String xhxt;

    @ApiModelProperty("放射治疗")
    private String fszl;

    @ApiModelProperty("化学药物治疗")
    private String hxywzl;

    @ApiModelProperty("手术治疗")
    private String sszl;

    @ApiModelProperty("其他是否异常")
    private String qt;

    @ApiModelProperty("其他异常备注,文本")
    private String qtycbz;


}
