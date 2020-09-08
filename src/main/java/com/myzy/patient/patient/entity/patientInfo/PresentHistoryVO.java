package com.myzy.patient.patient.entity.patientInfo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 现病史
 *
 * @author leekejin
 * @since 2020-8-7 11:38:01
 */
@Data
public class PresentHistoryVO {

    @ApiModelProperty("患者id")
    private Integer patientId;

    @ApiModelProperty("潮热及出汗")
    private String crjch;

    @ApiModelProperty("感觉障碍")
    private String gjza;

    @ApiModelProperty("失眠")
    private String sm;

    @ApiModelProperty("易激动")
    private String yjd;

    @ApiModelProperty("抑郁及疑心")
    private String yyjyx;

    @ApiModelProperty("眩晕")
    private String xy;

    @ApiModelProperty("疲乏")
    private String pf;

    @ApiModelProperty("骨关节痛")
    private String ggjt;

    @ApiModelProperty("头痛")
    private String tt;

    @ApiModelProperty("心悸")
    private String xj;

    @ApiModelProperty("皮肤蚁走感")
    private String pfyzg;

    @ApiModelProperty("泌尿系感染")
    private String mnxgr;

    @ApiModelProperty("性生活状况")
    private String xshzk;

    @ApiModelProperty("总分")
    private String zf;

    @ApiModelProperty("程度评价")
    private String cdpj;
}
