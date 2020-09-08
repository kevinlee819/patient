package com.myzy.patient.patient.entity.patientInfo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.myzy.patient.core.entity.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 患者的全部信息
 *
 * @author leekejin
 * @since 2020-8-5 10:00:01
 */
@Data
public class PatientAllInfoVO implements Serializable {

    /**
     * 以下是基本信息
     */
    @ApiModelProperty(value = "患者id")
    private Integer id;

    @ApiModelProperty(value = "患者姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String sex;
    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")

    @ApiModelProperty(value = "出生日期")
    private Date birth;

    @ApiModelProperty(value = "是否医保")
    private String isInsurance;

    @ApiModelProperty(value="联系方式")
    private String contactType;

    @ApiModelProperty(value="家庭电话")
    private String homePhone;

    @ApiModelProperty(value="地址")
    private String address;

    @ApiModelProperty(value="居住省")
    private String province;

    @ApiModelProperty(value="居住市")
    private String city;

    @ApiModelProperty(value="民族")
    private String nation;

    @ApiModelProperty(value="录入人")
    private String inputPeople;

    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value="录入时间")
    private Date inputDate;

    /**
     * 以下是就诊信息
     */

    @ApiModelProperty(value = "药品组，字符串直接传 络倍进 或 其他")
    private String drugGroup;

    @ApiModelProperty(value = "适应症，0-男，1-女")
    private String adaptDisease;

    @ApiModelProperty(value = "确诊医院")
    private String confirmHospital;

    @ApiModelProperty(value = "确诊医生")
    private String confirmDoctor;

    @ApiModelProperty(value = "购药医院")
    private String buyMedicineHospital;

    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "确诊时间")
    private Date confirmTime;

    @ApiModelProperty(value = "联合治疗")
    private String unionCure;

    @ApiModelProperty(value = "首次确认客服")
    private String firstService;



    /**
     * 既往史
     */
    @ApiModelProperty(value ="内分泌，0-否，1-是")
    private String nfmxt;

    @ApiModelProperty(value ="生殖，0-否，1-是")
    private String szxt;

    @ApiModelProperty(value ="呼吸，0-否，1-是")
    private String hxxt;

    @ApiModelProperty(value ="运动，0-否，1-是")
    private String ydxt;

    @ApiModelProperty(value ="中枢神经，0-否，1-是")
    private String zssjxt;

    @ApiModelProperty(value ="消化，0-否，1-是")
    private String xhxt;

    @ApiModelProperty(value ="放射治疗，0-否，1-是")
    private String fszl;

    @ApiModelProperty(value ="化学药物治疗，0-否，1-是")
    private String hxywzl;

    @ApiModelProperty(value ="手术治疗，0-否，1-是")
    private String sszl;

    @ApiModelProperty(value ="其他是否异常，0-否，1-是")
    private String qt;

    @ApiModelProperty(value ="其他异常备注,文本")
    private String qtycbz;

    /**
     * 现病史
     */
    @ApiModelProperty(value ="潮热及出汗")
    private String crjch;

    @ApiModelProperty(value ="感觉障碍")
    private String gjza;

    @ApiModelProperty(value ="失眠")
    private String sm;

    @ApiModelProperty(value ="易激动")
    private String yjd;

    @ApiModelProperty(value ="抑郁及疑心")
    private String yyjyx;

    @ApiModelProperty(value ="眩晕")
    private String xy;

    @ApiModelProperty(value ="疲乏")
    private String pf;

    @ApiModelProperty(value ="骨关节痛")
    private String ggjt;

    @ApiModelProperty(value ="头痛")
    private String tt;

    @ApiModelProperty(value ="心悸")
    private String xj;

    @ApiModelProperty(value ="皮肤蚁走感")
    private String pfyzg;

    @ApiModelProperty(value ="泌尿系感染")
    private String mnxgr;

    @ApiModelProperty(value ="性生活状况")
    private String xshzk;

    @ApiModelProperty(value ="总分")
    private String zf;

    @ApiModelProperty(value ="程度评价")
    private String cdpj;


    /**
     * 用药信息
     */
    @ApiModelProperty(value ="处方医院")
    private String prescriptionHospital;

    @ApiModelProperty(value ="处方医生")
    private String prescriptionDoctor;

    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value ="购药时间")
    private Date buyMedicineDate;

    @ApiModelProperty(value ="购药种类")
    private String buyMedicineType;

    @ApiModelProperty(value ="数量")
    private String quantity;

    @ApiModelProperty(value ="单价")
    private Double unitPrice;

    @ApiModelProperty(value ="总价")
    private Double totalPrice;

//    @ApiModelProperty(value ="是否赠药，0-不赠，1-赠")
//    private String isGiveMedicine;

    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty("可用药时间")
    private Date medicationTime;

    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value ="下次购药时间")
    private Date nextBuyMedicineDate;
}
